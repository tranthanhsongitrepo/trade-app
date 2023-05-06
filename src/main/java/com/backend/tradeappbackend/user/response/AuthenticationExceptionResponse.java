package com.backend.tradeappbackend.user.response;

import com.backend.tradeappbackend.response.GenericResponse;

public class AuthenticationExceptionResponse extends GenericResponse {
    public AuthenticationExceptionResponse() {
        super(401, "Login failed");
    }
}
