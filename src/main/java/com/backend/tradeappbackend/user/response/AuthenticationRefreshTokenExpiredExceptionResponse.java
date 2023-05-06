package com.backend.tradeappbackend.user.response;

import com.backend.tradeappbackend.response.GenericResponse;

public class AuthenticationRefreshTokenExpiredExceptionResponse extends GenericResponse {
    public AuthenticationRefreshTokenExpiredExceptionResponse() {
        super(401, "Refresh token expired");
    }
}
