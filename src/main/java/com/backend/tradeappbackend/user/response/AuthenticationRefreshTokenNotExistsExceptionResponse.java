package com.backend.tradeappbackend.user.response;

import com.backend.tradeappbackend.response.GenericResponse;

public class AuthenticationRefreshTokenNotExistsExceptionResponse extends GenericResponse {
    public AuthenticationRefreshTokenNotExistsExceptionResponse() {
        super(404, "Refresh token does not exists");
    }
}
