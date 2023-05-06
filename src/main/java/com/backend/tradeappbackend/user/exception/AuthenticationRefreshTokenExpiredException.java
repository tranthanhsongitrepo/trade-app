package com.backend.tradeappbackend.user.exception;

public class AuthenticationRefreshTokenExpiredException extends RuntimeException {
    public AuthenticationRefreshTokenExpiredException() {
        super("Token expired");
    }
}
