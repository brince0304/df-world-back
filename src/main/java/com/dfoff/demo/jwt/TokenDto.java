package com.dfoff.demo.jwt;

import com.dfoff.demo.domain.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@AllArgsConstructor
@Builder
public class TokenDto {
    private String accessToken;
    private String refreshToken;


    public static TokenDto from(UserAccount.PrincipalDto principal, String accessToken, String refreshToken) {
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
