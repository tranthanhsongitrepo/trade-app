package com.backend.tradeappbackend.user.response;

import com.backend.tradeappbackend.response.GenericResponse;

public class UserIdNotNullExceptionResponse extends GenericResponse {
    public UserIdNotNullExceptionResponse() {
        super(404, "User id must be null");
    }
}
