package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.CharacterEntityDto;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Service.CharacterService;
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



    private final Bcrypt bcrypt;

    @PostMapping("/api/user/login")
    public ResponseEntity<?> login(@RequestBody UserAccount.LoginDto dto) {
        try {
            log.info("login: {}", dto);
            UserAccount.UserAccountDTO accountDto = userAccountService.loginByUserId(dto);
            return new ResponseEntity<>(accountDto.getUserId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("잘못된 아이디나 비밀번호입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/user/validate")
    public String checkEmail(@RequestParam (required = false) String email,
                             @RequestParam (required = false) String nickname,
                             @RequestParam (required = false) String username) {
       if(email != null){
           log.info("email: {}", email);
           boolean result = userAccountService.existsByEmail(email);
           if(result){
               return "false";
           }
           return "true";
         }else if(nickname != null){
              log.info("nickname: {}", nickname);
              boolean result = userAccountService.existsByNickname(nickname);
              if(result){
                return "false";
              }
              return "true";
       }else if(username != null){
           log.info("username: {}", username);
              boolean result = userAccountService.existsByUserId(username);
                if(result){
                    return "false";
                }
       }return "true";
    }


    @GetMapping("/api/user/searchChar.df")
    public ResponseEntity<?> searchChar(@RequestParam(required = false) String serverId,
                                        @RequestParam(required = false) String characterName,
                                        @PageableDefault(size = 15) org.springframework.data.domain.Pageable pageable ,
                                        @AuthenticationPrincipal UserAccount.PrincipalDto userAccountDTO) {
        try {
            if (userAccountDTO == null) {
                return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
            }
            if (serverId == null || characterName == null) {
                return new ResponseEntity<>("서버와 캐릭터 이름을 입력해주세요.", HttpStatus.BAD_REQUEST);
            }
            if (serverId.equals("adventure")) {
                return new ResponseEntity<>(characterService.getCharacterByAdventureName(characterName, pageable).map(CharacterEntityDto.CharacterEntityResponse::from).toList(), HttpStatus.OK);
            }
            List<CompletableFuture<CharacterEntityDto>> dtos = new ArrayList<>();
            List<CharacterEntityDto> dtos1 = characterService.getCharacterDTOs(serverId, characterName);
            for (CharacterEntityDto dto : dtos1.subList(0, Math.min(dtos1.size(), 15))) {
                if(dto.getLevel()>=100) {
                    dtos.add(characterService.getCharacterAbilityThenSaveAsync(dto));
                }else{
                    dtos.add(CompletableFuture.completedFuture(dto));
                }
            }
            int size = Math.min(dtos.size(), 15);
            return new ResponseEntity<>(dtos.stream().map(CompletableFuture::join).map(CharacterEntityDto.CharacterEntityResponse::from).collect(Collectors.toList()).subList(0,size), HttpStatus.OK);
            } catch(Exception e){
                log.info("error: {}", e.getMessage());
                return new ResponseEntity<>("캐릭터를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
            }
        }


    @PostMapping("/api/user")
    public ResponseEntity<?> createAccount(@RequestBody UserAccount.UserAccountSignUpRequest request){
        try {
            log.info("singUp: {}", request);
            if (request.getPassword().equals(request.getPasswordCheck())) {
                UserAccount.UserAccountDTO dto = request.toDto();
                dto.setPassword(bcrypt.encode(request.getPassword()));
                dto.setProfileIcon(saveFileService.getFileByFileName("icon_char_0.png"));
                if (userAccountService.createAccount(dto)) {
                    return new ResponseEntity<>(request.getUserId(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/user/char.df")
    public ResponseEntity<?> addCharacter(@RequestParam (required = false) String request,
                                          @RequestParam (required = false) String characterId,
                                          @AuthenticationPrincipal UserAccount.PrincipalDto userAccountDTO) {
        try {
            if(userAccountDTO == null){
                return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
            }
            if(request.equals("add")){
                characterService.addCharacter(UserAccount.UserAccountDTO.from(userAccountDTO), characterService.getCharacter(characterId));
            }else if(request.equals("delete")){
                characterService.deleteCharacter(UserAccount.UserAccountDTO.from(userAccountDTO), characterService.getCharacter(characterId));
            }
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/myPage.df")
    public ModelAndView getMyPage(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto){
        try {
            if (principalDto == null) {
                return new ModelAndView("redirect:/main.df");
            }
            ModelAndView mav = new ModelAndView("/mypage/mypage");
            UserAccount.UserAccountDTO userAccountDTO = userAccountService.getUserAccountById(principalDto.getUsername());
            mav.addObject("user", UserAccount.UserAccountResponse.from(userAccountDTO));
            mav.addObject("characters", userAccountDTO.getCharacterEntityDtos().stream().map(CharacterEntityDto.CharacterEntityResponse::from).collect(Collectors.toSet()));
            return mav;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ModelAndView("redirect:/main.df");
        }
    }

    @PutMapping("/api/user/profile.df")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto,
                                           @RequestParam (required = false) String nickname,
                                           @RequestParam (required = false) String email,
                                           @RequestParam (required = false) String password,
                                             @RequestParam (required = false) String profileIcon){

        try {
            if(principalDto == null){
                return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.BAD_REQUEST);
            }
            if(password != null){
                if(userAccountService.chagePassword(UserAccount.UserAccountDTO.from(principalDto), password)){
                    return new ResponseEntity<>("비밀번호가 변경되었습니다.", HttpStatus.OK);
                }
            }
            if(profileIcon != null){
                if(userAccountService.changeProfileIcon(UserAccount.UserAccountDTO.from(principalDto),saveFileService.getFileByFileName(profileIcon))){
                    return new ResponseEntity<>("프로필이 변경되었습니다.", HttpStatus.OK);
                }
            }
            if(nickname != null){
                if(userAccountService.changeNickname(UserAccount.UserAccountDTO.from(principalDto),nickname)){
                    return new ResponseEntity<>("닉네임이 변경되었습니다.", HttpStatus.OK);
                }
            }
            if(email != null){
                if(userAccountService.changeEmail(UserAccount.UserAccountDTO.from(principalDto),email)){
                    return new ResponseEntity<>("이메일이 변경되었습니다.", HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    }



