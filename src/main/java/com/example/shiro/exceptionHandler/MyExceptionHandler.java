package com.example.shiro.exceptionHandler;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    public String notAuthorized(){
        return "login";
    }
}
