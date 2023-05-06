package com.backend.tradeappbackend.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTTokenPair {
    private String accessToken;
    private String refreshToken;
}
