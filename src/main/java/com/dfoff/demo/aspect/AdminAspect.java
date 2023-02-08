package com.dfoff.demo.aspect;

import com.dfoff.demo.domain.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AdminAspect {

    @Pointcut("@annotation(com.dfoff.demo.annotation.AdminCheck)")
    public void adminCheck() {
    }

    @Before("adminCheck()&&args(principal,..)")
    public void adminCheck(UserAccount.PrincipalDto principal) throws IllegalAccessException {
       principal.getAuthorities().stream().filter(authority -> authority.getAuthority().equals("ROLE_ADMIN")).findAny().orElseThrow(()->new IllegalAccessException("관리자만 접근 가능합니다."));
    }
}
