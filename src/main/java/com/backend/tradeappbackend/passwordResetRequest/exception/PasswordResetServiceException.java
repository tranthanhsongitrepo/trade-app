package com.backend.tradeappbackend.passwordResetRequest.exception;

public class PasswordResetServiceException extends Exception {
    public PasswordResetServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordResetServiceException(String message) {
        super(message);
    }
}
