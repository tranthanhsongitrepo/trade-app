package com.backend.tradeappbackend.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.backend.tradeappbackend.filter.response.TokenRevokedResponse;
import com.backend.tradeappbackend.filter.response.UserLoggedInOnAnotherDeviceResponse;
import com.backend.tradeappbackend.jwt.DecodedAuthorizationDetails;
import com.backend.tradeappbackend.jwt.JWTDecoder;
import com.backend.tradeappbackend.response.TokenExpiredExceptionResponse;
import com.backend.tradeappbackend.user.EmailNotFoundExceptionResponse;
import com.backend.tradeappbackend.user.User;
import com.backend.tradeappbackend.user.UserRepository;
import com.backend.tradeappbackend.user.exception.UserNotFoundException;
import com.backend.tradeappbackend.user.response.UsernameNotFoundExceptionResponse;
import com.backend.tradeappbackend.userAuthority.UserAuthority;
import com.backend.tradeappbackend.userAuthority.UserAuthorityRepository;
import com.backend.tradeappbackend.userRole.UserRole;
import com.backend.tradeappbackend.userRole.UserRoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JWTDecoder jwtDecoder;
    private final UserRepository userRepository;

    private final String[] skipPaths = {
            "/api/v1/auth/user/login",
            "/api/v1/auth/user/refresh",
    };
    private final UserAuthorityRepository userAuthorityRepository;
    private final UserRoleRepository userRoleRepository;

    public JwtAuthorizationFilter(JWTDecoder jwtDecoder, UserRepository userRepository, UserAuthorityRepository userAuthorityRepository, UserRoleRepository userRoleRepository) {
        this.jwtDecoder = jwtDecoder;
        this.userRepository = userRepository;
        this.userAuthorityRepository = userAuthorityRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setContentType(APPLICATION_JSON_VALUE);

        if (request.getServletPath().equals("/api/v1/auth/login")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    DecodedAuthorizationDetails decodedJWT = this.jwtDecoder.decode(token);

                    User user = this.userRepository.findUserByEmail(decodedJWT.getEmail());

                    if (user == null) {
                        new ObjectMapper().writeValue(response.getOutputStream(), new TokenRevokedResponse());
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }

                    boolean isUserLoggedOut = user.getCurrentAccessTokenUUID() == null;
                    if (isUserLoggedOut) {
                        new ObjectMapper().writeValue(response.getOutputStream(), new TokenRevokedResponse());
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }

                    boolean isUserLoggedInOnAnotherDevice = !Objects.equals(user.getCurrentAccessTokenUUID(), decodedJWT.getLoggedInDeviceId());
                    if (isUserLoggedInOnAnotherDevice) {
                        new ObjectMapper().writeValue(response.getOutputStream(), new UserLoggedInOnAnotherDeviceResponse());
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }

                    List<UserAuthority> userAuthorities = this.userAuthorityRepository.findUserAuthoritiesByUserId(user.getUserId());
                    user.setUserAuthorities(userAuthorities);

                    List<UserRole> userRoles = this.userRoleRepository.findUserRolesByUserId(user.getUserId());
                    user.setUserRoles(userRoles);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (TokenExpiredException e) {
                    new ObjectMapper().writeValue(response.getOutputStream(), new TokenExpiredExceptionResponse());
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        for (String skipPath : this.skipPaths) {
            if (new AntPathMatcher().match(skipPath, path)) {
                return true;
            }
        }
        return false;
    }
}
