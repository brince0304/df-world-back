package com.dfoff.demo.oauth;

import com.dfoff.demo.domain.UserAccount;
import com.dfoff.demo.jwt.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.h2.engine.User;

@Data
@AllArgsConstructor
@Builder
public class OAuthDto {
    private TokenDto tokenDto;
    private UserAccount.UserAccountDto userAccountDto;


    public static OAuthDto from(UserAccount.UserAccountDto userAccountDto, TokenDto tokenDto) {
        return OAuthDto.builder()
                .userAccountDto(userAccountDto)
                .tokenDto(tokenDto)
                .build();
    }
}