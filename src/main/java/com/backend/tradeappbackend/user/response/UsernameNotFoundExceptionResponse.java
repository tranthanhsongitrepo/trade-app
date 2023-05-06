package com.backend.tradeappbackend.user.response;

import com.backend.tradeappbackend.response.GenericResponse;

public class UsernameNotFoundExceptionResponse extends GenericResponse {
    public UsernameNotFoundExceptionResponse(String message) {
        this.setStatus(404);
        this.setMessage(message);
    }
}
