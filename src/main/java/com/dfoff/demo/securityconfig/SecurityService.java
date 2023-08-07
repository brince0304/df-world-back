package com.dfoff.demo.securityconfig;

import com.dfoff.demo.domain.enums.useraccount.SecurityRole;
import com.dfoff.demo.domain.SaveFile;
import com.dfoff.demo.domain.UserAccount;
import com.dfoff.demo.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
        Optional<UserAccount> _account = userAccountRepository.findById(username);
        if (_account.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        if(_account.get().getDeleted()!=null&&_account.get().getDeleted()) {
            throw new UsernameNotFoundException("탈퇴한 사용자입니다.");
        }
        UserAccount account = _account.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (SecurityRole role : account.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getValue()));
        }
        return UserAccount.PrincipalDto.builder()
                .username(account.getUserId())
                .password(account.getPassword())
                .nickname(account.getNickname())
                .email(account.getEmail())
                .profileIcon(Objects.requireNonNull(SaveFile.SaveFileDto.from(account.getProfileIcon())))
                .authorities(authorities)
                .build();
    }
        catch (Exception e){
            log.error("SecurityService loadUserByUsername error",e);
            throw e;
        }
    }
}
