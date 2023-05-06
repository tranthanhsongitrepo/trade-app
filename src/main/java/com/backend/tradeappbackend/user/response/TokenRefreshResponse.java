package com.backend.tradeappbackend.user.response;

import com.backend.tradeappbackend.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TokenRefreshResponse extends GenericResponse {
    private final String accessToken;

    public TokenRefreshResponse(String accessToken) {
        super(200, "Token refreshed");
        this.accessToken = accessToken;
    }
}
