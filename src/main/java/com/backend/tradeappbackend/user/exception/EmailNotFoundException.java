package com.backend.tradeappbackend.user.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String email) {
        super("User with email %s not found".formatted(email));
    }
}
