package com.dfoff.demo.jwt;

import com.dfoff.demo.domain.UserAccount;
import com.dfoff.demo.securityconfig.SecurityService;
import com.dfoff.demo.service.RedisService;
import com.dfoff.demo.utils.CookieUtil;
import com.dfoff.demo.utils.ErrorMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.parser.Token;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final SecurityService securityService;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie accessTokenCookie = CookieUtil.getCookie(TokenProvider.ACCESS_TOKEN_NAME, request);
        Cookie refreshTokenCookie = CookieUtil.getCookie(TokenProvider.REFRESH_TOKEN_NAME, request);
        String refreshJwt;
        String refreshUname;
        String accessJwt;
        String accessUname;
        log.info("access token cookie : {}", accessTokenCookie);
        try {
            // 쿠키가 둘다 존재하지 않으면 컨텍스트 해제
            if (accessTokenCookie == null && refreshTokenCookie == null) {
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request, response);
                return;
            }
            // 액세스 쿠키 존재하면 해당 쿠키 토큰값으로 인증 컨텍스트 부여
            if (accessTokenCookie != null) {
                accessJwt = accessTokenCookie.getValue();
                // 토큰 만료 검사를 getUsername 메소드가 호출되면서 진행하기 때문에 매번 따로 expired 체크를 하지 않음
                accessUname = tokenProvider.getUsername(accessJwt);
                // 토큰 만료가 되지 않고 컨텍스트 정보가 없을때
                if (accessUname != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    log.info("username : {}", accessUname);
                    UserDetails userDetails = securityService.loadUserByUsername(accessUname);
                    // 로그아웃 되지 않은 정상적인 토큰이고 토큰의 유저네임과 계정 아이디가 같을 때
                    if (tokenProvider.validateToken(accessUname) && userDetails.getUsername().equals(accessUname)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }else{
                refreshJwt = refreshTokenCookie.getValue();
                refreshUname = tokenProvider.getUsername(refreshJwt);
                UserAccount.PrincipalDto dto = (UserAccount.PrincipalDto) securityService.loadUserByUsername(refreshUname);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dto, null, dto.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                accessTokenCookie = CookieUtil.createAccessTokenCookie(tokenProvider.generateToken(dto),TokenProvider.TOKEN_VALIDATION_SECOND );
                response.addCookie(accessTokenCookie);
            }
            // 액세스 토큰 만료됐을 때 && 리프레쉬 토큰은 만료되면 쿠키가 없기 때문에 예외 발생 X
        } catch (ExpiredJwtException e) {
            log.warn(e.getMessage());
            log.warn("access token expired, reissue access token");
            // 레디스까지 체크하기 이전에 이미 토큰 값을 갖고있는 쿠키가 존재하는지 확인
            if (refreshTokenCookie != null) {
                refreshJwt = refreshTokenCookie.getValue();
                refreshUname = tokenProvider.getUsername(refreshJwt);
                String redisJwt = redisService.get(TokenProvider.REFRESH_TOKEN_NAME + refreshUname);
                if (refreshJwt.equals(redisJwt)) {
                    UserAccount.PrincipalDto dto = (UserAccount.PrincipalDto) securityService.loadUserByUsername(refreshUname);
                    accessTokenCookie = CookieUtil.createAccessTokenCookie(tokenProvider.doGenerateToken(dto, TokenProvider.TOKEN_VALIDATION_SECOND), TokenProvider.REFRESH_TOKEN_VALIDATION_SECOND);
                    response.addCookie(accessTokenCookie);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dto, null, dto.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }else{
                accessTokenCookie = CookieUtil.createAccessTokenCookie("",0);
                response.addCookie(accessTokenCookie);
            }
        }
        filterChain.doFilter(request, response);
    }
}
