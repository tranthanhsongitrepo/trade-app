package com.backend.tradeappbackend.user;

import com.backend.tradeappbackend.response.GenericResponse;

public class JWTDecodeExceptionResponse extends GenericResponse {
    public JWTDecodeExceptionResponse() {
        super(400, "The provided JWT could not be decoded");
    }
}
