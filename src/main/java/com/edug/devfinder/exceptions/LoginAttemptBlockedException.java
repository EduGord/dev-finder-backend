package com.edug.devfinder.exceptions;

import org.springframework.security.core.AuthenticationException;

public class LoginAttemptBlockedException extends AuthenticationException {

    public LoginAttemptBlockedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public LoginAttemptBlockedException(String msg) {
        super(msg);
    }
}
