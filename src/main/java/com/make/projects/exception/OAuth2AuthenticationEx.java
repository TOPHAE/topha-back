package com.make.projects.exception;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationEx extends AuthenticationException {


    public OAuth2AuthenticationEx(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OAuth2AuthenticationEx(String msg) {
        super(msg);
    }
}
