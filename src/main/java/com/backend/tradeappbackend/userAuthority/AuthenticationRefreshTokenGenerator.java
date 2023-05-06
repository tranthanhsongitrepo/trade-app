package com.backend.tradeappbackend.userAuthority;

import java.util.UUID;

public class AuthenticationRefreshTokenGenerator implements RefreshTokenGenerator {
    @Override
    public String generateRefreshToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
