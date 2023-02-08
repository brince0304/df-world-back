package com.dfoff.demo.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class BasicControllerAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(EntityNotFoundException exception) {
        log.error(exception.getMessage());
        return new ModelAndView ("errorPage/404",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ModelAndView handleHttpClientErrorException(HttpClientErrorException exception) {
        log.error(exception.getMessage());
        return new ModelAndView (exception.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentExceptionException(IllegalArgumentException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(IllegalAccessException.class)
    public ModelAndView handleIllegalAccessException(IllegalAccessException exception) {
        log.error(exception.getMessage());
        return new ModelAndView("errorPage/403", HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(SecurityException.class)
    public ModelAndView handleSecurityExceptionException(SecurityException exception) {
        log.error(exception.getMessage());
        return new ModelAndView("errorPage/401", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingServletRequestParameterException
            (MissingServletRequestParameterException
                     exception) {
        log.error(exception.getMessage());
        return new ModelAndView("errorPage/404", HttpStatus.NOT_FOUND);
    }





}
