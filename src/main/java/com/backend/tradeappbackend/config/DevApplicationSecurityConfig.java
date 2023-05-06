package com.backend.tradeappbackend.config;

import com.backend.tradeappbackend.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class DevApplicationSecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    public DevApplicationSecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
//                authenticationManager((AuthenticationConfiguration) http.getSharedObject(AuthenticationManager.class))
//        );
//        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/user/login");

        http
                .authorizeHttpRequests().
//                    requestMatchers(HttpMethod.GET, "/**").permitAll().
        requestMatchers(HttpMethod.POST, "/api/v1/auth/user/login/**").anonymous().
                requestMatchers(HttpMethod.GET, "/api/v1/auth/user/login/**").anonymous().
                requestMatchers(HttpMethod.GET, "/api/v1/auth/user/logout/**").authenticated().
                requestMatchers(HttpMethod.POST, "/api/v1/auth/user/").anonymous().
                requestMatchers(HttpMethod.PUT, "/api/v1/auth/user/").authenticated().
                requestMatchers(HttpMethod.POST, "/api/v1/auth/staff/login").anonymous().
                requestMatchers(HttpMethod.POST, "/api/v1/auth/staff/").anonymous().
                requestMatchers(HttpMethod.POST, "/api/v1/auth/user/delete/**").anonymous().
                requestMatchers(HttpMethod.POST, "/api/v1/auth/password-reset/**").anonymous().
                requestMatchers(HttpMethod.GET, "/api/v1/auth/staff/**").hasAnyRole("ADMIN", "STAFF").
                requestMatchers(HttpMethod.GET, "/api/v1/admin/**").hasRole("ADMIN").
                requestMatchers(HttpMethod.POST, "/api/v1/admin/**").hasRole("ADMIN").
                requestMatchers(HttpMethod.PUT, "/api/v1/admin/**").hasRole("ADMIN").
                requestMatchers(HttpMethod.DELETE, "/api/v1/admin/**").hasRole("ADMIN").
                requestMatchers(HttpMethod.GET, "/api/v1/auth/user/{id}").hasAnyRole("ADMIN").
                requestMatchers(HttpMethod.GET, "/api/v1/auth/user/refresh/**").permitAll().
                requestMatchers(HttpMethod.GET, "/api/v1/socketId/**").permitAll().
                requestMatchers("/error/**").permitAll().
                anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/api/v1/auth/user/logout")
                .logoutSuccessUrl("/api/v1/auth/user/logout-success").permitAll()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
//                    .addFilter(jwtAuthenticationFilter)
                .csrf()
                .disable()
//                .formLogin()
//                    .loginPage("/api/v1/auth/user/login")
//                    .loginProcessingUrl("/api/v1/auth/user/login")
//                    .defaultSuccessUrl("/api/v1/auth/user/login-success")
//                    .failureUrl("/api/v1/auth/user/login-failure")
//                    .permitAll()
        ;

        http.addFilterBefore(this.jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList("http://localhost:8081"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
