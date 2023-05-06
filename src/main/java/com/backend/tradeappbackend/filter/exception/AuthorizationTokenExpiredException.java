package com.backend.tradeappbackend.filter.exception;

public class AuthorizationTokenExpiredException extends RuntimeException {
    public AuthorizationTokenExpiredException() {
        super("Token expired");
    }
}
