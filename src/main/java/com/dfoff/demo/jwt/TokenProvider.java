package com.dfoff.demo.jwt;

import com.dfoff.demo.domain.UserAccount;
import com.dfoff.demo.service.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider {
    private final RedisService redisService;
    public final static int TOKEN_VALIDATION_SECOND = 1000 * 60 * 60;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 24 * 7; // 7일

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    @Value("${spring.jwt.secret}")
    private String TOKEN_SECRET_KEY;

    public TokenProvider(RedisService redisService) {
        this.redisService = redisService;
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    } //서명키를 시크릿키를 바탕으로 암호화해 설정한다.

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(TOKEN_SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    } // 식별을 위해 저장했거나 저장된 값들을 다시 가져오기 위한 메소드(압축해제와 비슷하다.)

    public String getUsername(String token) {
        return extractAllClaims(token).get("username", String.class);
    } //payload 에서 유저이름을 가져온다.

    public String getAuthorities(String token) {
        return extractAllClaims(token).get("authorities", String.class);
    } //payload 에서 권한을 가져온다.

    public String getNickname(String token) {
        return extractAllClaims(token).get("nickname", String.class);
    } //payload 에서 닉네임을 가져온다.

    public Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    } //토큰이 만료되었는지 확인해주는 메소드

    public String generateToken(UserAccount.PrincipalDto member) {
        return doGenerateToken(member, TOKEN_VALIDATION_SECOND);
    }

    public String generateRefreshToken(UserAccount.PrincipalDto member) {
        return doGenerateToken(member, REFRESH_TOKEN_VALIDATION_SECOND);
    }
    //액세스토큰과 리프레쉬 토큰의 만료시간 설정

    public String doGenerateToken(UserAccount.PrincipalDto dto, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("username", dto.getUsername());
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(TOKEN_SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    } //토큰을 생성해주는 메소드 (claim에 식별을 위한 다양한 정보를 저장할 수 있다.)


    public long getExpireTime(String token) {
        return extractAllClaims(token).getExpiration().getTime();
    } //토큰의 만료시간을 반환해주는 메소드

    public Boolean validateToken(String token) {
        return !redisService.hasBlacklist("LOGOUT_" + token);
    } //토큰을 검증해주는 메소드. 탈취당한 토큰이라면 접근을 거부시킨다.
}
