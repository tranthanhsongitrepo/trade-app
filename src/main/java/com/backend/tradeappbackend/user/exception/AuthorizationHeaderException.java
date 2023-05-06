package com.backend.tradeappbackend.user.exception;

public class AuthorizationHeaderException extends RuntimeException {
    public AuthorizationHeaderException() {
        super("Cannot find bearer token in header");
    }

}
