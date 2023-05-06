package com.backend.tradeappbackend.config;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimiterConfig {
    @Bean
    public RateLimiter authenRateLimiter() {
        return RateLimiter.create(0.1d);
    }
}
