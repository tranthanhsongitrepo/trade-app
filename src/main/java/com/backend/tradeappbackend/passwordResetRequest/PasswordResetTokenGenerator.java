package com.backend.tradeappbackend.passwordResetRequest;

import java.security.SecureRandom;

public class PasswordResetTokenGenerator {
    public String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[128];
        random.nextBytes(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
