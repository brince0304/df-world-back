package com.dfoff.demo.utils;

import com.dfoff.demo.jwt.TokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CookieUtil {
    public static Cookie createAccessTokenCookie(String token, long expireTime) {
        Cookie cookie = new Cookie(TokenProvider.ACCESS_TOKEN_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) expireTime);
        cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }

    public static Cookie createRefreshTokenCookie(String token, long expireTime) {
        Cookie cookie = new Cookie(TokenProvider.REFRESH_TOKEN_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) expireTime);
        cookie.setSecure(true);
        cookie.setPath("/");
        return cookie;
    }

    public static Cookie getCookie(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

}
