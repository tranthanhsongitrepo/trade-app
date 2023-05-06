package com.backend.tradeappbackend.user.exception;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String email) {
        super("User with email %s already exists".formatted(email));
    }
}
