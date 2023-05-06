package com.backend.tradeappbackend.passwordResetRequest.response;

import com.backend.tradeappbackend.response.GenericResponse;

public class PasswordResetRequestCreatedResponse extends GenericResponse {
    public PasswordResetRequestCreatedResponse() {
        this.setMessage("Password reset request created successfully");
    }
}
