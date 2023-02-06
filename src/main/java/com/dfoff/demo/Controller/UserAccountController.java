package com.dfoff.demo.Controller;

import com.dfoff.demo.Annotation.Auth;
import com.dfoff.demo.Domain.Adventure;
import com.dfoff.demo.Domain.CharacterEntity;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Service.CharacterService;
import com.dfoff.demo.Service.NotificationService;
import com.dfoff.demo.Service.SaveFileService;
import com.dfoff.demo.Service.UserAccountService;
import com.dfoff.demo.Util.Bcrypt;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            if (userAccountService.existsByEmail(email)) {
                return "false";
            }
            return "true";
        } else if (nickname != null) {
            if (userAccountService.existsByNickname(nickname)) {
                return "false";
            }
            return "true";
        } else if (username != null) {
            if (userAccountService.existsByUserId(username)) {
                return "false";
            }
        }
        return "true";
    }

    @PostMapping("/users/adventure")
    public ResponseEntity<?> createUserAdventure(@AuthenticationPrincipal UserAccount.PrincipalDto principal,
                                                 @RequestBody Adventure.UserAdventureRequest request) throws InterruptedException {
        if (request.getAdventureName() == null) {
            return new ResponseEntity<>("모험단 이름을 다시 입력해주세요.", HttpStatus.BAD_REQUEST);
        }
        if (characterService.checkCharacterAdventure(request)) {
            CharacterEntity.CharacterEntityDto character = characterService.getCharacter(request.getServerId(), request.getRepresentCharacterId());
            userAccountService.saveUserAdventure(request, UserAccount.UserAccountDto.from(principal), character,characterService.getCharactersByAdventureName(request.getAdventureName()));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("다시 시도해주세요.", HttpStatus.BAD_REQUEST);
    }

    @Auth
    @GetMapping("/users/adventure/validate")
    public ResponseEntity<?> getRandomJobNameAndString(@AuthenticationPrincipal UserAccount.PrincipalDto principal) {
        String randomJobName = characterService.getRandomJobName();
        String randomString = characterService.getRandomString();
        HashMap<String, String> map = new HashMap<>();
        map.put("randomJobName", "마법사(여)");
        map.put("randomString", "소라");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Auth
    @GetMapping("/users/adventure/refresh")
    public ResponseEntity<?> refreshUserAdventure(@AuthenticationPrincipal UserAccount.PrincipalDto principal) throws InterruptedException {
        String adventureName = userAccountService.getUserAdventureNameByUserId(principal.getUsername());
       Adventure.UserAdventureDto dto = userAccountService.addAllCharactersToUserAdventure(UserAccount.UserAccountDto.from(principal),characterService.getCharactersByAdventureName(adventureName));
        for (CharacterEntity.CharacterEntityDto character :dto.getCharacters()) {
            characterService.getCharacterAbility(character);
        }
        userAccountService.refreshUserAdventure(UserAccount.UserAccountDto.from(principal));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Auth
    @GetMapping("/users/characters/")
    public ResponseEntity<?> searchCharacterForUserAccount(@AuthenticationPrincipal UserAccount.PrincipalDto principal, @RequestParam String serverId,
                                                           @RequestParam String characterName,
                                                           @PageableDefault(size = 15) org.springframework.data.domain.Pageable pageable) throws InterruptedException {
        if (serverId.equals("adventure")) {
            return new ResponseEntity<>(characterService.getCharacterByAdventureName(characterName, pageable), HttpStatus.OK);
        }
        List<CharacterEntity.CharacterEntityDto> dtos = new ArrayList<>();
        List<CharacterEntity.CharacterEntityDto> dtos1 = characterService.getCharacterDtos(serverId, characterName).join();
        return getCharacterResponse(dtos, dtos1, characterService);
    }


    static ResponseEntity<?> getCharacterResponse(List<CharacterEntity.CharacterEntityDto> dtos, List<CharacterEntity.CharacterEntityDto> dtos1, CharacterService characterService) throws InterruptedException {
        for (CharacterEntity.CharacterEntityDto dto : dtos1.subList(0, Math.min(dtos1.size(), 15))) {
            if (dto.getLevel() >= 110) {
                dtos.add(characterService.getCharacterAbility(dto));
            } else {
                dtos.add(dto);
            }
        }
        int size = Math.min(dtos.size(), 15);
        return new ResponseEntity<>(dtos.stream().map(CharacterEntity.CharacterEntityDto.CharacterEntityResponse::from).collect(Collectors.toList()).subList(0, size), HttpStatus.OK);
    }


    @PostMapping("/users")
    public ResponseEntity<?> createAccount(@RequestBody @Valid UserAccount.UserAccountSignUpRequest request, BindingResult bindingResult) {
        if (!request.getPassword().equals(request.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }
        request.setPassword(bcrypt.encode(request.getPassword()));
        userAccountService.createAccount(request, saveFileService.getFileByFileName("icon_char_0.png"));
        return new ResponseEntity<>(request.getUserId(), HttpStatus.OK);
    }

    @Auth
    @PostMapping("/users/characters")
    public ResponseEntity<?> addCharacterToUserAccount(@AuthenticationPrincipal UserAccount.PrincipalDto principal,
                                                       @RequestParam(required = false) String serverId,
                                                       @RequestParam(required = false) String characterId) throws InterruptedException {
        userAccountService.addCharacter(UserAccount.UserAccountDto.from(principal), characterService.getCharacterAbility(characterService.getCharacter(serverId, characterId)));
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Auth
    @DeleteMapping("/users/characters")
    public ResponseEntity<?> deleteCharacter(@AuthenticationPrincipal UserAccount.PrincipalDto principal,
                                             @RequestParam(required = false) String serverId,
                                             @RequestParam(required = false) String characterId
    ) throws InterruptedException {
        userAccountService.deleteCharacter(UserAccount.UserAccountDto.from(principal), characterService.getCharacter(serverId, characterId));
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Auth
    @GetMapping("/users/my-page")
    public ModelAndView getMyPage(@AuthenticationPrincipal UserAccount.PrincipalDto principal) {
        ModelAndView mav = new ModelAndView("/mypage/mypage");
        UserAccount.UserAccountMyPageResponse response = userAccountService.getUserAccountById(principal.getUsername());
        mav.addObject("user", response);
        mav.addObject("uncheckedLogCount", notificationService.getUncheckedNotificationCount(principal.getUsername()));
        return mav;
    }

    @Auth
    @GetMapping("/users/logs/")
    public ResponseEntity<?> getLog(@AuthenticationPrincipal UserAccount.PrincipalDto principal,
                                    @RequestParam String type,
                                    @PageableDefault(size = 15) org.springframework.data.domain.Pageable pageable,
                                    @RequestParam(required = false) String sortBy) {
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
        }
        if (type.equals("comment")) {
            if (sortBy.equals("like")) {
                return new ResponseEntity<>(userAccountService.getCommentsByUserIdOrderByLikeCount(principal.getUsername(), pageable), HttpStatus.OK);
            }
            return new ResponseEntity<>(userAccountService.getCommentsByUserId(principal.getUsername(), pageable), HttpStatus.OK);
        }
        if (type.equals("log")) {
            return new ResponseEntity<>(notificationService.getUserNotifications(principal.getUsername(), pageable), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Auth
    @PutMapping("/users")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserAccount.PrincipalDto principal,
                                           @RequestParam(required = false) String nickname,
                                           @RequestParam(required = false) String email,
                                           @RequestParam(required = false) String password,
                                           @RequestParam(required = false) String profileIcon) {
        if (password != null && !password.equals("")) {
            if(!password.matches("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$")){
                throw new IllegalArgumentException("비밀번호는 영문, 숫자, 특수문자를 포함하여 8~20자리로 입력해주세요.");
            }
            if (userAccountService.changePassword(UserAccount.UserAccountDto.from(principal), bcrypt.encode(password))) {
                return new ResponseEntity<>("비밀번호가 변경되었습니다.", HttpStatus.OK);
            }
        }
        if (profileIcon != null && !profileIcon.equals("")) {
            if (userAccountService.changeProfileIcon(UserAccount.UserAccountDto.from(principal), saveFileService.getFileByFileName(profileIcon))) {
                return new ResponseEntity<>("프로필이 변경되었습니다.", HttpStatus.OK);
            }
        }
        if (nickname != null && !nickname.equals("")) {
            if(userAccountService.existsByNickname(nickname)){
                throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
            }
            if(!nickname.matches("^[a-zA-Z0-9가-힣]{2,10}$")){
                throw new IllegalArgumentException("닉네임은 2~10자리로 입력해주세요.");
            }
            if (userAccountService.changeNickname(UserAccount.UserAccountDto.from(principal), nickname)) {
                return new ResponseEntity<>("닉네임이 변경되었습니다.", HttpStatus.OK);
            }
        }
        if (email != null && !email.equals("")) {
            if(userAccountService.existsByEmail(email)){
                throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
            }
            if(!email.matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")){
                throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
            }
            if (userAccountService.changeEmail(UserAccount.UserAccountDto.from(principal), email)) {
                return new ResponseEntity<>("이메일이 변경되었습니다.", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}



