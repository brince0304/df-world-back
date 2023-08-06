package com.dfoff.demo.service;

import com.dfoff.demo.domain.SaveFile;
import com.dfoff.demo.domain.UserAccount;
import com.dfoff.demo.jwt.TokenDto;
import com.dfoff.demo.jwt.TokenProvider;
import com.dfoff.demo.oauth.OAuthDto;
import com.dfoff.demo.oauth.OAuthInfoResponse;
import com.dfoff.demo.oauth.OAuthLoginParams;
import com.dfoff.demo.repository.UserAccountRepository;
import com.dfoff.demo.utils.Bcrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OAuthLoginService {
    private final UserAccountRepository userAccountRepository;
    private final TokenProvider tokenProvider;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final Bcrypt bcrypt;


    @Transactional
    public OAuthDto login(OAuthLoginParams params, SaveFile.SaveFileDto profileIcon) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        UserAccount.UserAccountDto memberDto = findOrCreateMember(oAuthInfoResponse, profileIcon);
        UserAccount.PrincipalDto principal = UserAccount.PrincipalDto.from(memberDto);
        return OAuthDto.from(memberDto, TokenDto.from(principal,tokenProvider.generateToken(principal), tokenProvider.generateRefreshToken(principal)));
    }

    @Transactional(readOnly = true)
    public void authenticateUser(UserAccount.UserAccountDto userAccountDto) {
        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(new UsernamePasswordAuthenticationToken(userAccountDto.userId(), userAccountDto.email()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.error("authenticateUser error", e);
        }
    }

    @Transactional
    public UserAccount.UserAccountDto findOrCreateMember(OAuthInfoResponse oAuthInfoResponse, SaveFile.SaveFileDto profileIcon) {
        UserAccount userAccount =  userAccountRepository.findByEmail(oAuthInfoResponse.getEmail())
                .orElseGet(() -> {
                    UserAccount.UserAccountDto accountDto = UserAccount.UserAccountDto.from(oAuthInfoResponse);
                    return userAccountRepository.save(accountDto.toEntity());
                });
        userAccount.setProfileIcon(profileIcon.toEntity());
        userAccount.setPassword(bcrypt.encode(oAuthInfoResponse.getEmail()));
        return UserAccount.UserAccountDto.from(userAccount);
    }

}