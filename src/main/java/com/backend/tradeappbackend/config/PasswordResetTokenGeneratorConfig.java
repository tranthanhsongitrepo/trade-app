package com.backend.tradeappbackend.config;

import com.backend.tradeappbackend.passwordResetRequest.PasswordResetTokenGenerator;
import org.springframework.context.annotation.Bean;

public class PasswordResetTokenGeneratorConfig {
    @Bean
    public PasswordResetTokenGenerator passwordResetTokenGenerator() {
        return new PasswordResetTokenGenerator();
    }
}
