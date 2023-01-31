package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.CharacterEntity;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Domain.UserAdventure;
import com.dfoff.demo.Service.CharacterService;
import com.dfoff.demo.Service.NotificationService;
import com.dfoff.demo.Service.SaveFileService;
import com.dfoff.demo.Service.UserAccountService;
import com.dfoff.demo.Util.Bcrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final SaveFileService saveFileService;

    private final CharacterService characterService;

    private final NotificationService notificationService;


    private final Bcrypt bcrypt;


    @GetMapping("/users/check")
    public String checkExist(@RequestParam(required = false) String email,
                             @RequestParam(required = false) String nickname,
                             @RequestParam(required = false) String username) {
        if (email != null) {
            log.info("email: {}", email);
            boolean result = userAccountService.existsByEmail(email);
            if (result) {
                return "false";
            }
            return "true";
        } else if (nickname != null) {
            log.info("nickname: {}", nickname);
            boolean result = userAccountService.existsByNickname(nickname);
            if (result) {
                return "false";
            }
            return "true";
        } else if (username != null) {
            log.info("username: {}", username);
            boolean result = userAccountService.existsByUserId(username);
            if (result) {
                return "false";
            }
        }
        return "true";
    }

    @PostMapping("/users/adventure")
    public ResponseEntity<?> createUserAdventure(@AuthenticationPrincipal UserAccount.PrincipalDto dto,
                                             @RequestBody UserAdventure.UserAdventureRequest request) throws InterruptedException {
        log.info("request: {}", request);
        if(request.getAdventureName() == null) {
            return new ResponseEntity<>("모험단 이름을 다시 입력해주세요.", HttpStatus.BAD_REQUEST);
        }
        if(dto==null){
            throw new SecurityException("로그인이 필요합니다.");
        }
        if(characterService.checkCharacterAdventure(request)){
            CharacterEntity.CharacterEntityDto character = characterService.getCharacter(request.getServerId(), request.getRepresentCharacterId()).join();
            userAccountService.saveUserAdventure(request, UserAccount.UserAccountDto.from(dto), character);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("다시 시도해주세요.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/users/adventure/validate")
    public ResponseEntity<?> getRandomJobNameAndString(@AuthenticationPrincipal UserAccount.PrincipalDto dto){
        if(dto==null){
            throw new SecurityException("로그인이 필요합니다.");
        }
        String randomJobName = characterService.getRandomJobName();
        String randomString = characterService.getRandomString();
        HashMap<String,String> map = new HashMap<>();
        map.put("randomJobName", randomJobName);
        map.put("randomString", randomString);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @GetMapping("/users/adventure/refresh")
    public ResponseEntity<?> refreshUserAdventure(@AuthenticationPrincipal UserAccount.PrincipalDto dto ){
        if(dto==null){
            throw new SecurityException("로그인이 필요합니다.");
        }
        userAccountService.refreshUserAdventure(UserAccount.UserAccountDto.from(dto));
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @GetMapping("/users/characters/")
    public ResponseEntity<?> searchCharacterForUserAccount(@RequestParam(required = false) String serverId,
                                                           @RequestParam(required = false) String characterName,
                                                           @PageableDefault(size = 15) org.springframework.data.domain.Pageable pageable,
                                                           @AuthenticationPrincipal UserAccount.PrincipalDto principal) throws InterruptedException {
            if (principal == null) {
                throw new SecurityException("로그인이 필요합니다.");
            }
            if (serverId == null || characterName == null) {
                throw new IllegalArgumentException("서버 아이디, 캐릭터 아이디는 필수입니다.");
            }
            if (serverId.equals("adventure")) {
                return new ResponseEntity<>(characterService.getCharacterByAdventureName(characterName, pageable).map(CharacterEntity.CharacterEntityDto.CharacterEntityResponse::from).toList(), HttpStatus.OK);
            }
            List<CompletableFuture<CharacterEntity.CharacterEntityDto>> dtos = new ArrayList<>();
            List<CharacterEntity.CharacterEntityDto> dtos1 = characterService.getCharacterDtos(serverId, characterName).join();
        return getCharacterResponse(dtos, dtos1, characterService);
    }

    static ResponseEntity<?> getCharacterResponse(List<CompletableFuture<CharacterEntity.CharacterEntityDto>> dtos, List<CharacterEntity.CharacterEntityDto> dtos1, CharacterService characterService) throws InterruptedException {
        for (CharacterEntity.CharacterEntityDto dto : dtos1.subList(0, Math.min(dtos1.size(), 15))) {
            if (dto.getLevel() >= 100) {
                dtos.add(characterService.getCharacterAbilityAsync(dto));
            } else {
                dtos.add(CompletableFuture.completedFuture(dto));
            }
        }
        int size = Math.min(dtos.size(), 15);
        return new ResponseEntity<>(dtos.stream().map(CompletableFuture::join).map(CharacterEntity.CharacterEntityDto.CharacterEntityResponse::from).collect(Collectors.toList()).subList(0, size), HttpStatus.OK);
    }


    @PostMapping("/users")
    public ResponseEntity<?> createAccount(@RequestBody UserAccount.UserAccountSignUpRequest request) {
        if (!request.getPassword().equals(request.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        userAccountService.createAccount(request, saveFileService.getFileByFileName("icon_char_0.png"));
            return new ResponseEntity<>(request.getUserId(), HttpStatus.OK);
    }

    @PostMapping("/users/characters")
    public ResponseEntity<?> addCharacterToUserAccount(
                                          @RequestParam(required = false) String serverId,
                                          @RequestParam(required = false) String characterId,
                                          @AuthenticationPrincipal UserAccount.PrincipalDto principal) throws InterruptedException {
        if (principal == null) {
            throw new SecurityException("로그인이 필요합니다.");
        }
            characterService.getCharacterAbilityAsync(characterService.getCharacter(serverId, characterId).join());
            characterService.addCharacter(UserAccount.UserAccountDto.from(principal), characterService.getCharacter(serverId, characterId).join());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/users/characters")
    public ResponseEntity<?> deleteCharacter(
                                          @RequestParam(required = false) String serverId,
                                          @RequestParam(required = false) String characterId,
                                          @AuthenticationPrincipal UserAccount.PrincipalDto principal) {
        if (principal == null) {
            throw new SecurityException("로그인이 필요합니다.");
        }
            characterService.deleteCharacter(UserAccount.UserAccountDto.from(principal), characterService.getCharacter(serverId, characterId).join());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/users/my-page")
    public ModelAndView getMyPage(@AuthenticationPrincipal UserAccount.PrincipalDto principal) {
        if (principal == null) {
            throw new SecurityException("로그인이 필요합니다.");
        }
        ModelAndView mav = new ModelAndView("/mypage/mypage");
        UserAccount.UserAccountMyPageResponse response = userAccountService.getUserAccountById(principal.getUsername());
        mav.addObject("user", response);
        mav.addObject("uncheckedLogCount", notificationService.getUncheckedNotificationCount(principal.getUsername()));
        return mav;
    }

    @GetMapping("/users/logs/")
    public ResponseEntity<?> getLog(@AuthenticationPrincipal UserAccount.PrincipalDto principal,
                                    @RequestParam String type,
                                    @PageableDefault(size = 15) org.springframework.data.domain.Pageable pageable,
                                    @RequestParam (required = false) String sortBy) {
        if (principal == null) {
            throw new SecurityException("로그인이 필요합니다.");
        }
        if (type.equals("board")) {
            return switch (sortBy) {
                case "like" ->
                        new ResponseEntity<>(userAccountService.getBoardsByUserIdOrderByLikeCount(principal.getUsername(), pageable), HttpStatus.OK);
                case "commentCount" ->
                        new ResponseEntity<>(userAccountService.getBoardsByUserIdOrderByCommentCount(principal.getUsername(), pageable), HttpStatus.OK);
                case "view" ->
                        new ResponseEntity<>(userAccountService.getBoardsByUserIdOrderByViewCount(principal.getUsername(), pageable), HttpStatus.OK);
                default ->
                        new ResponseEntity<>(userAccountService.getBoardsByUserId(principal.getUsername(), pageable), HttpStatus.OK);
            };
        } if (type.equals("comment")) {
            if(sortBy.equals("like")) {
                return new ResponseEntity<>(userAccountService.getCommentsByUserIdOrderByLikeCount(principal.getUsername(), pageable), HttpStatus.OK);
            }
            return new ResponseEntity<>(userAccountService.getCommentsByUserId(principal.getUsername(), pageable), HttpStatus.OK);
        }
        if(type.equals("log")){
            return new ResponseEntity<>(notificationService.getUserNotifications(principal.getUsername(), pageable), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



    @PutMapping("/users")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto,
                                           @RequestParam(required = false) String nickname,
                                           @RequestParam(required = false) String email,
                                           @RequestParam(required = false) String password,
                                           @RequestParam(required = false) String profileIcon) {
        if (principalDto == null) {
            throw new SecurityException("로그인이 필요합니다.");
        }
        if (password != null) {
            if (userAccountService.chagePassword(UserAccount.UserAccountDto.from(principalDto), password)) {
                return new ResponseEntity<>("비밀번호가 변경되었습니다.", HttpStatus.OK);
            }
        }
        if (profileIcon != null) {
            if (userAccountService.changeProfileIcon(UserAccount.UserAccountDto.from(principalDto), saveFileService.getFileByFileName(profileIcon))) {
                return new ResponseEntity<>("프로필이 변경되었습니다.", HttpStatus.OK);
            }
        }
        if (nickname != null) {
            if (userAccountService.changeNickname(UserAccount.UserAccountDto.from(principalDto), nickname)) {
                return new ResponseEntity<>("닉네임이 변경되었습니다.", HttpStatus.OK);
            }
        }
        if (email != null) {
            if (userAccountService.changeEmail(UserAccount.UserAccountDto.from(principalDto), email)) {
                return new ResponseEntity<>("이메일이 변경되었습니다.", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}



