package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.UserAccount;
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
import java.util.Set;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final SaveFileService saveFileService;


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
    @PostMapping("/api/user/id")
    public String checkId(@RequestBody Map<String,String> map) {
        log.info("username: {}", map.get("username"));
        boolean result = userAccountService.existsByUserId(map.get("username"));
        if(result){
            return "false";
        }
        return "true";
    }
    @PostMapping("/api/user/nickname")
    public String checkNickname(@RequestBody Map<String,String> map) {
        log.info("nickname: {}", map.get("nickname"));
        boolean result = userAccountService.existsByNickname(map.get("nickname"));
        if(result){
            return "false";
        }
        return "true";
    }
    @PostMapping("/api/user/email")
    public String checkEmail(@RequestBody Map<String,String> map) {
        log.info("email: {}", map.get("email"));
        boolean result = userAccountService.existsByEmail(map.get("email"));
        if(result){
            return "false";
        }
        return "true";
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
            return mav;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ModelAndView("redirect:/main.df");
        }

    }


}
