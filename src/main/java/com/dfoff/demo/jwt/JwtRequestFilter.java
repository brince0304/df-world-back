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
        String refreshJwt ;
        String refreshUname;
        String accessJwt;
        String accessUname;

        log.info("access token cookie : {}", accessTokenCookie);

        try {
            if(accessTokenCookie == null && refreshTokenCookie == null) {
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request, response);
                return;
            }
            if (accessTokenCookie != null) {
                accessJwt = accessTokenCookie.getValue();
                accessUname = tokenProvider.getUsername(accessJwt);
                if (accessUname != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    log.info("username : {}", accessUname);
                    UserDetails userDetails = securityService.loadUserByUsername(accessUname);
                    if (tokenProvider.validateToken(accessJwt) && userDetails.getUsername().equals(accessUname) ) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }
        } catch (ExpiredJwtException e) {
            log.warn(e.getMessage());
            log.warn("access token expired");
            if(refreshTokenCookie!=null){
                refreshJwt = refreshTokenCookie.getValue();
                refreshUname = tokenProvider.getUsername(refreshJwt);
                log.info("refresh username : {}", refreshUname);
                if (refreshUname != null ) {
                    UserDetails userDetails = securityService.loadUserByUsername(refreshUname);
                    if (userDetails.getUsername().equals(refreshUname) && redisService.get(TokenProvider.REFRESH_TOKEN_NAME + refreshUname).equals(refreshJwt)) {
                        response.addCookie(CookieUtil.createAccessTokenCookie(tokenProvider.generateToken((UserAccount.PrincipalDto) userDetails), TokenProvider.TOKEN_VALIDATION_SECOND));
                    } else {
                        log.warn("refresh token expired");
                        response.addCookie(CookieUtil.createAccessTokenCookie("", 0));
                        response.addCookie(CookieUtil.createRefreshTokenCookie("", 0));
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ErrorMessage.JWT_NOTVALID);
                    }
                }
            }
        } catch (JwtException | UsernameNotFoundException e) {
            log.warn(e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ErrorMessage.JWT_NOTVALID);
        }
        filterChain.doFilter(request, response);
    }
}
