package com.dfoff.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Aspect
@Component
@Slf4j
public class BindingErrorAspect {
    @Pointcut("@annotation(com.dfoff.demo.annotation.BindingErrorCheck)")
    public void bindingErrorCheck() {
    }

    @Around("bindingErrorCheck()&&args(..,bindingResult)")
    public Object bindingErrorCheck(ProceedingJoinPoint pjp, BindingResult bindingResult) throws Throwable {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        return pjp.proceed();
    }
}
