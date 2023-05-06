package com.backend.tradeappbackend.response;

public class TokenExpiredExceptionResponse extends GenericResponse {
    public TokenExpiredExceptionResponse() {
        super(403, "Token expired");
    }
}
