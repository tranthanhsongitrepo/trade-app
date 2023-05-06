package com.backend.tradeappbackend.passwordResetRequest;

import com.backend.tradeappbackend.passwordResetRequest.exception.PasswordResetServiceException;
import com.backend.tradeappbackend.passwordResetRequest.response.PasswordResetServiceExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PasswordResetRequestExceptionHandler {
    @ExceptionHandler(value = PasswordResetServiceException.class)
    public ResponseEntity<?> handlePasswordResetServiceException(PasswordResetServiceException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new PasswordResetServiceExceptionResponse(500, e.getMessage()));
    }
}
