package com.backend.tradeappbackend.refreshToken;

import com.backend.tradeappbackend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_authentication_refresh_token", indexes = @Index(name = "idx_refresh_token", columnList = "hashedRefreshTokenString", unique = true))
@Data
@NoArgsConstructor
@AllArgsConstructor
@PropertySource("classpath:application.properties")
public class AuthenticationRefreshToken implements RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authRefreshTokenId;

    @Column(nullable = false, unique = true)
    private String hashedRefreshTokenString;

    private boolean isRevoked = false;

    private LocalDateTime createdDate;

    private LocalDateTime expiryDate;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId", unique = true)
    private User user;

    public AuthenticationRefreshToken(String refreshTokenString, LocalDateTime createdDate, LocalDateTime expiryDate, User user) {
        this.hashedRefreshTokenString = refreshTokenString;
        this.createdDate = createdDate;
        this.expiryDate = expiryDate;
        this.user = user;
    }

    public AuthenticationRefreshToken(LocalDateTime createdDate, LocalDateTime expiryDate, User user) {
        this.createdDate = createdDate;
        this.expiryDate = expiryDate;
        this.user = user;
    }

    @Override
    public boolean isRevoked() {
        return this.isRevoked;
    }

    @Override
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }
}
