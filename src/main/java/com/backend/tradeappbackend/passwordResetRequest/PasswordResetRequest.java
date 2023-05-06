package com.backend.tradeappbackend.passwordResetRequest;

import com.backend.tradeappbackend.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_password_reset_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {
    @Transient
    private final int EXPIRATION = 15;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId", unique = true)
    private User user;
    private String token;
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdDate = LocalDateTime.now();

    public boolean isTokenExpired() {
        return LocalDateTime.now().isAfter(this.createdDate.plusMinutes(EXPIRATION));
    }
}
