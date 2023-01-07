package com.dfoff.demo.Service;

import com.dfoff.demo.Domain.AccountCharacterConnector;
import com.dfoff.demo.Domain.SaveFile;
import com.dfoff.demo.Domain.UserAccount;
import com.dfoff.demo.Repository.AccountCharacterConnectorRepository;
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

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final Bcrypt bcrypt;

    private final AccountCharacterConnectorRepository connectorRepository;


    public boolean createAccount(UserAccount.UserAccountDTO account) {
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
        UserAccount account0 = userAccountRepository.save(UserAccount.UserAccountDTO.toEntity(account));
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



    public boolean updateAccountDetails(UserAccount.UserAccountDTO request) {
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
    public void connectAccountAndCharacter(UserAccount.UserAccountDTO dto, String characterId) {
        UserAccount account = userAccountRepository.findById(dto.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        connectorRepository.save(AccountCharacterConnector.builder()
                .userAccount(account)
                .characterId(characterId)
                .build());
    }

    public List<AccountCharacterConnector.AccountCharacterConnectorDTO> getCharacterListByAccount(UserAccount.UserAccountDTO dto) {
        UserAccount account = userAccountRepository.findById(dto.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        List<AccountCharacterConnector.AccountCharacterConnectorDTO> connectorList = connectorRepository.findByUserAccount(account).stream().map(AccountCharacterConnector.AccountCharacterConnectorDTO::from).collect(Collectors.toList());
        if(connectorList.size() == 0) {
            return new ArrayList<>();
        }
        return connectorList;
    }

    public void changeProfileIcon (UserAccount.UserAccountDTO dto, SaveFile.SaveFileDTO iconDto){
        UserAccount account = userAccountRepository.findById(dto.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        account.setProfileIcon(SaveFile.SaveFileDTO.toEntity(iconDto));
    }


    @Transactional(readOnly = true)
    public UserAccount.UserAccountDTO getUserAccountById(String userId) {
        if(userAccountRepository.existsByUserId(userId)){
            return UserAccount.UserAccountDTO.from(userAccountRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다.")));
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

    public UserAccount.UserAccountDTO loginByUserId(UserAccount.LoginDto dto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("authentication: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return UserAccount.UserAccountDTO.builder().build();
    }


}
