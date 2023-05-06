package com.backend.tradeappbackend.refreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AuthenticationRefreshTokenRepository extends JpaRepository<AuthenticationRefreshToken, Long> {
    @Query("SELECT NEW AuthenticationRefreshToken(t.createdDate, t.expiryDate, t.user) FROM AuthenticationRefreshToken t WHERE t.hashedRefreshTokenString = :hashedToken")
    AuthenticationRefreshToken findAuthenticationRefreshTokenByHashedRefreshTokenString(String hashedToken);

    @Modifying(flushAutomatically = true)
    @Query("UPDATE AuthenticationRefreshToken art SET art.isRevoked = true WHERE art.user IN (SELECT u FROM User u WHERE u.email = :email)")
    void revokeRefreshTokenByUserEmail(String email);
}
