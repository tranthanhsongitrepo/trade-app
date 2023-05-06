package com.backend.tradeappbackend.user.exception;

public class AuthenticationRefreshTokenNotExistsException extends RuntimeException {
    public AuthenticationRefreshTokenNotExistsException() {
        super("Token does not exists");
    }
}
