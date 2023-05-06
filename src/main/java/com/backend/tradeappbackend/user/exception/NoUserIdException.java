package com.backend.tradeappbackend.user.exception;

public class NoUserIdException extends RuntimeException {
    public NoUserIdException() {
        super("User ID must not be empty");
    }
}
