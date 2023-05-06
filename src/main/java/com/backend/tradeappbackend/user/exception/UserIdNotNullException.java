package com.backend.tradeappbackend.user.exception;

public class UserIdNotNullException extends RuntimeException {
    public UserIdNotNullException() {
        super("User ID must be empty");
    }
}
