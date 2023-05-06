package com.backend.tradeappbackend.user.response;

import com.backend.tradeappbackend.response.GenericResponse;
import com.backend.tradeappbackend.user.User;
import lombok.Data;

@Data
public class UserResponse extends GenericResponse {
    private final User user;

    public UserResponse(User user) {
        this.user = user;
    }
}
