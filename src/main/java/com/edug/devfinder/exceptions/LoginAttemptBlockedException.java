package com.edug.devfinder.exceptions;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

public class LoginAttemptBlockedException extends AuthenticationException {

    @Serial
    private static final long serialVersionUID = 1L;

    public LoginAttemptBlockedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public LoginAttemptBlockedException(String msg) {
        super(msg);
    }
}
