package com.backend.tradeappbackend.passwordResetRequest.response;

import com.backend.tradeappbackend.response.GenericResponse;

public class PasswordResetServiceExceptionResponse extends GenericResponse {
    public PasswordResetServiceExceptionResponse(int status, String message) {
        this.setStatus(status);
        this.setMessage(message);
    }
}
