package com.backend.tradeappbackend.config;

import com.backend.tradeappbackend.filter.JwtAuthorizationFilter;
import com.backend.tradeappbackend.jwt.JWTDecoder;
import com.backend.tradeappbackend.user.UserRepository;
import com.backend.tradeappbackend.userAuthority.UserAuthorityRepository;
import com.backend.tradeappbackend.userRole.UserRoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtAuthorizationFilterConfig {
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(JWTDecoder jwtDecoder, UserRepository userRepository, UserAuthorityRepository userAuthorityRepository, UserRoleRepository userRoleRepository) {
        return new JwtAuthorizationFilter(jwtDecoder, userRepository, userAuthorityRepository, userRoleRepository);
    }

}
