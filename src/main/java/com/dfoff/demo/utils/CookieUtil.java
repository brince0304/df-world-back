package com.dfoff.demo.utils;

import com.dfoff.demo.jwt.TokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseCookie;

@UtilityClass
public class CookieUtil {
    public static ResponseCookie createAccessTokenCookie(String token, long expireTime) {
        ResponseCookie cookie = ResponseCookie.from(TokenProvider.ACCESS_TOKEN_NAME, token)
.httpOnly(true)
.secure(true)
.maxAge((int) expireTime / 1000)
.path("/")
.sameSite("None")
.build();

        return cookie;
    }

    public static ResponseCookie createRefreshTokenCookie(String token, long expireTime) {
        ResponseCookie cookie = ResponseCookie.from(TokenProvider.REFRESH_TOKEN_NAME, token)
.httpOnly(true)
.secure(true)
.maxAge((int) expireTime / 1000)
.path("/")
.sameSite("None")
.build();
        return cookie;
    }

    public static Cookie getCookieFromResponseCookie(ResponseCookie responseCookie) {
        Cookie cookie = new Cookie(responseCookie.getName(), responseCookie.getValue());
        cookie.setHttpOnly(responseCookie.isHttpOnly());
        cookie.setSecure(responseCookie.isSecure());
        cookie.setMaxAge(responseCookie.getMaxAge().toMinutesPart());
        cookie.setPath(responseCookie.getPath());
        cookie.setDomain(responseCookie.getDomain());
        return cookie;
    }

    public static ResponseCookie createExpiredCookie(String name) {
        ResponseCookie cookie = ResponseCookie.from(name, "")
.httpOnly(true)
.secure(true)
.maxAge(0)
.path("/")
.sameSite("None")
.build();
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
