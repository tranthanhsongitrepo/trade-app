package com.backend.tradeappbackend.user.response;

import com.backend.tradeappbackend.response.GenericResponse;

public class AuthorizationHeaderExceptionResponse extends GenericResponse {
    public AuthorizationHeaderExceptionResponse() {
        super(401, "Cannot find bearer token in header");
    }
}
