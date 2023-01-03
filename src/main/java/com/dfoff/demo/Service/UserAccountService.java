package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.EnumType.SecurityRole;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.UserAccountRepository;
import com.dfoff.demo.Util.Bcrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final Bcrypt bcrypt;


    public boolean createAccount(UserAccount.UserAccountDto account) {
        if (userAccountRepository.existsByUserId(account.getUserId())) {
            throw new EntityExistsException("이미 존재하는 아이디입니다.");
        }
        if (userAccountRepository.existsByEmail(account.getEmail())) {
            throw new EntityExistsException("이미 존재하는 이메일입니다.");
        }
        if (userAccountRepository.existsByNickname(account.getNickname())) {
            throw new EntityExistsException("이미 존재하는 닉네임입니다.");
        }
        log.info("account: {}", account);
        userAccountRepository.save(account.toEntity());
        return true;
    }



    public boolean updateAccountDetails(UserAccount.UserAccountDto request) {
        UserAccount account = userAccountRepository.findById(request.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if (userAccountRepository.existsByEmail(request.getEmail())) {
            log.info("이미 존재하는 이메일입니다.");
            return false;
        }
        if (userAccountRepository.existsByNickname(request.getNickname())) {
            log.info("이미 존재하는 닉네임입니다.");
            return false;
        }
        if(request.getEmail() != null) {
            account.setEmail(request.getEmail());
        }
        if(request.getNickname() != null) {
            account.setNickname(request.getNickname());
        }
        return true;
    }
    @Transactional(readOnly = true)
    public UserAccount.UserAccountDto getUserAccountById(String userId) {
        if(userAccountRepository.existsByUserId(userId)){
            return UserAccount.UserAccountDto.from(userAccountRepository.getReferenceById(userId));
        }
        return UserAccount.UserAccountDto.builder().build();
    }

    public boolean deleteUserAccountById(String userId) {
        if(userAccountRepository.existsByUserId(userId)){
            userAccountRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public UserAccount.UserAccountDto loginByUserId(UserAccount.LoginDto dto) {
        UserAccount account = userAccountRepository.findById(dto.getUsername()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return UserAccount.UserAccountDto.builder().build();
    }
}
