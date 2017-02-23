package com.coastcapitalsavings.auth.jwt;

import org.springframework.security.core.AuthenticationException;


public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String event, Throwable t){
        super(event, t);
    }
}
