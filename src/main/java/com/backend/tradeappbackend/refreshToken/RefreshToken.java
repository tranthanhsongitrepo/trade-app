package com.backend.tradeappbackend.refreshToken;

public interface RefreshToken {
    boolean isRevoked();

    boolean isExpired();
}
