package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Service.CharacterService;
import com.dfoff.demo.Service.SaveFileService;
import com.dfoff.demo.Service.UserAccountService;
import com.dfoff.demo.Util.Bcrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
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

    @GetMapping("/user/myPage.df")
    public ModelAndView getMyPage(@AuthenticationPrincipal UserAccount.PrincipalDto principalDto){
        try {
            if (principalDto == null) {
                return new ModelAndView("redirect:/main.df");
            }
            ModelAndView mav = new ModelAndView("/mypage/mypage");
            mav.addObject("user", UserAccount.UserAccountResponse.of(userAccountService.getUserAccountById(principalDto.getUsername())));
            mav.addObject("characters", characterService.getCharacterAbilityList(userAccountService.getCharacterListByAccount(UserAccount.UserAccountDTO.from(principalDto))));
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
                                           @RequestParam (required = false) String passwordCheck,
                                             @RequestParam (required = false) String profileIcon){

        try {
            userAccountService.changeProfileIcon(UserAccount.UserAccountDTO.from(principalDto),saveFileService.getFileByFileName(profileIcon));
            return new ResponseEntity<>("프로필 사진이 변경되었습니다.", HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    }



