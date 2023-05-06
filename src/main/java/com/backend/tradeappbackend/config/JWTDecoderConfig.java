package com.backend.tradeappbackend.config;

import com.backend.tradeappbackend.jwt.JWTDecoder;
import com.backend.tradeappbackend.jwt.LocalJWTDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTDecoderConfig {
    @Value("${JWT_SECRET}")
    private String secret;

    @Bean
    public JWTDecoder jwtDecoderFactory() {
        return new LocalJWTDecoder(secret);
    }
}
