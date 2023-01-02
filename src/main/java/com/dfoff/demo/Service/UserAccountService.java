package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountService {
    UserAccountRepository userAccountRepository;


    public boolean createAccount(UserAccount.UserAccountDto account) {
        if (userAccountRepository.existsByUserId(account.getUserId())) {
            log.info("이미 존재하는 아이디입니다.");
            return false;
        }
        if (userAccountRepository.existsByEmail(account.getEmail())) {
            log.info("이미 존재하는 이메일입니다.");
            return false;
        }
        if (userAccountRepository.existsByNickname(account.getNickname())) {
            log.info("이미 존재하는 닉네임입니다.");
            return false;
        }
        userAccountRepository.save(account.toEntity());
        return true;

    }

    public boolean updateAccountDetails(UserAccount.UserAccountUpdateRequest request) {
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
        if(request.getPassword() !=null && request.getPassword().equals(request.getPasswordCheck())) {
            account.setPassword(request.getPassword());
        }
        return true;
    }

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
}
