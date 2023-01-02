package com.dfoff.demo.Controller;

import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Service.UserAccountService;
import com.dfoff.demo.Util.Bcrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final Bcrypt bcrypt;


    @PostMapping("/api/user")
    public ResponseEntity<?> createAccount(@RequestBody UserAccount.UserAccountSignUpRequest request){
        try {
            log.info("회원가입 요청");
            if (request.getPassword().equals(request.getPasswordCheck())) {
                UserAccount.UserAccountDto dto = request.toDto();
                dto.setPassword(bcrypt.encode(request.getPassword()));
                if (userAccountService.createAccount(dto)) {
                    return new ResponseEntity<>(request.getUserId(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            log.error("회원가입 실패");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
