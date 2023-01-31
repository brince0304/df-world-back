package com.dfoff.demo.Interceptor;

import com.dfoff.demo.Domain.UserAccount;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
public class BoardInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        Authentication authentication = session.getAttribute("AUTHENTICATE") != null ? (Authentication) session.getAttribute("AUTHENTICATE") : null;
        // 로그인 안됨
        if(authentication == null) {
            response.sendRedirect("/boards/");
            return false;
        } return true;
    }
}
