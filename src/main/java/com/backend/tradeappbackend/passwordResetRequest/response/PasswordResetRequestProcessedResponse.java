package com.backend.tradeappbackend.passwordResetRequest.response;

import com.backend.tradeappbackend.response.GenericResponse;

public class PasswordResetRequestProcessedResponse extends GenericResponse {
    public PasswordResetRequestProcessedResponse() {
        this.setMessage("Password reset request processed successfully");
    }
}
