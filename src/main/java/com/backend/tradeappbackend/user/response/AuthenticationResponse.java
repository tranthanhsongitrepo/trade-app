package com.backend.tradeappbackend.user.response;

import com.backend.tradeappbackend.jwt.JWTTokenPair;
import com.backend.tradeappbackend.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthenticationResponse extends GenericResponse {
    private final JWTTokenPair jwtTokenPair;

    public AuthenticationResponse(String message, JWTTokenPair jwtTokenPair) {
        super(200, message);
        this.jwtTokenPair = jwtTokenPair;
    }
}
