package com.backend.tradeappbackend.filter.response;

import com.backend.tradeappbackend.response.GenericResponse;

public class UserLoggedInOnAnotherDeviceResponse extends GenericResponse {
    public UserLoggedInOnAnotherDeviceResponse() {
        super(403, "User logged in using another device");
    }
}
