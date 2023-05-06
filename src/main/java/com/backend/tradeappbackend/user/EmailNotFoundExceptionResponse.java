package com.backend.tradeappbackend.user;

import com.backend.tradeappbackend.response.GenericResponse;

public class EmailNotFoundExceptionResponse extends GenericResponse {
    public EmailNotFoundExceptionResponse(String message) {
        super(404, message);
    }
}
