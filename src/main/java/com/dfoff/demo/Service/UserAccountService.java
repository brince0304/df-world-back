package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.SaveFile;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.UserAccountRepository;
import com.dfoff.demo.Util.Bcrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final Bcrypt bcrypt;



    public boolean createAccount(UserAccount.UserAccountDto account, SaveFile.SaveFileDTO profileIcon) {
        if (userAccountRepository.existsByUserId(account.userId())) {
            throw new EntityExistsException("이미 존재하는 아이디입니다.");
        }
        if (userAccountRepository.existsByEmail(account.email())) {
            throw new EntityExistsException("이미 존재하는 이메일입니다.");
        }
        if (userAccountRepository.existsByNickname(account.nickname())) {
            throw new EntityExistsException("이미 존재하는 닉네임입니다.");
        }
        log.info("account: {}", account);
        UserAccount account0 = userAccountRepository.save(account.toEntity());
        account0.setPassword(bcrypt.encode(account0.getPassword()));
        account0.setProfileIcon(profileIcon.toEntity());
        return true;
    }


    public boolean existsByUserId(String userId) {
        return userAccountRepository.existsByUserId(userId);
    }

    public boolean existsByEmail(String email) {
        return userAccountRepository.existsByEmail(email);
    }

    public boolean existsByNickname(String nickname) {
        return userAccountRepository.existsByNickname(nickname);
    }



    public boolean updateAccountDetails(UserAccount.UserAccountDto request) {
        UserAccount account = userAccountRepository.findById(request.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if (userAccountRepository.existsByEmail(request.email())) {
            log.info("이미 존재하는 이메일입니다.");
            return false;
        }
        if (userAccountRepository.existsByNickname(request.nickname())) {
            log.info("이미 존재하는 닉네임입니다.");
            return false;
        }
        if(request.email() != null) {
            account.setEmail(request.email());
        }
        if(request.nickname() != null) {
            account.setNickname(request.nickname());
        }
        return true;
    }



    public boolean changeProfileIcon (UserAccount.UserAccountDto dto, SaveFile.SaveFileDTO iconDto){
        UserAccount account = userAccountRepository.findById(dto.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        account.setProfileIcon(iconDto.toEntity());
        return true;
    }


    @Transactional(readOnly = true)
    public UserAccount.UserAccountDto getUserAccountById(String userId) {
        if(userAccountRepository.existsByUserId(userId)){
            return UserAccount.UserAccountDto.from(userAccountRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다.")));
        }
        return null;
    }

    public boolean deleteUserAccountById(String userId) {
        if(userAccountRepository.existsByUserId(userId)){
            userAccountRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public UserAccount.UserAccountDto loginByUserId(UserAccount.LoginDto dto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("authentication: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return UserAccount.UserAccountDto.builder().build();
    }


    public boolean chagePassword(UserAccount.UserAccountDto accountDTO, String password) {
        UserAccount account = userAccountRepository.findById(accountDTO.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        account.setPassword(bcrypt.encode(password));
        return true;
    }

    public boolean changeNickname(UserAccount.UserAccountDto accountDTO, String nickname) {
        UserAccount account = userAccountRepository.findById(accountDTO.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if(userAccountRepository.existsByNickname(nickname)){
            return false;
        }
        account.setNickname(nickname);
        return true;
    }

    public boolean changeEmail(UserAccount.UserAccountDto accountDTO, String email) {
        UserAccount account = userAccountRepository.findById(accountDTO.userId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if(userAccountRepository.existsByEmail(email)){
            return false;
        }
        account.setEmail(email);
        return true;
    }
}
