package com.backend.tradeappbackend.filter.exception;

public class UserNotLoggedOutException extends RuntimeException {
    public UserNotLoggedOutException() {
        super("User not logged out");
    }
}
