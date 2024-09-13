package com.igrowker.miniproject.exceptions;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

public class BadCredentialsException extends AuthenticationCredentialsNotFoundException {
    public BadCredentialsException(String message) {
        super(message);
    }
}
