package com.backend.tradeappbackend.user.response;

import com.backend.tradeappbackend.response.GenericResponse;

public class LogoutResponse extends GenericResponse {
    public LogoutResponse(String message) {
        super(0, message);
    }
}
