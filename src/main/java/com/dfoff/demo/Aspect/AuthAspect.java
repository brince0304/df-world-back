package com.dfoff.demo.Aspect;

import com.dfoff.demo.Domain.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class AuthAspect {
    @Pointcut("@annotation(com.dfoff.demo.Annotation.Auth)")
    public void auth() {
    }

    @Before("auth()&&args(principal,..)")
    public void authCheck(UserAccount.PrincipalDto principal) {
        if (principal == null) {
            throw new SecurityException("로그인이 필요합니다.");
        }
    }

}

