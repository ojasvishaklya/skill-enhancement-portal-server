package com.telstra.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public AuthenticationException(String exMessage) {
        super(exMessage);
    }
}
