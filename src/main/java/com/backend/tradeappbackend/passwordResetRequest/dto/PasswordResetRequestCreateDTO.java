package com.backend.tradeappbackend.passwordResetRequest.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequestCreateDTO {
    @Email
    private String email;
}
