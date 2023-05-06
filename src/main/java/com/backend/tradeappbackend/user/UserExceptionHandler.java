package com.backend.tradeappbackend.user;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.backend.tradeappbackend.response.TokenExpiredExceptionResponse;
import com.backend.tradeappbackend.user.exception.*;
import com.backend.tradeappbackend.user.response.*;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(value = EmailExistsException.class)
    public ResponseEntity<?> handleUsernameExistsException(EmailExistsException e) {
        return ResponseEntity.ok(
                new UsernameExistsExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new UsernameNotFoundExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(value = FirebaseAuthException.class)
    public ResponseEntity<?> handleFirebaseAuthException(FirebaseAuthException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new AuthenticationResponse(e.getMessage(), null));
    }

    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<?> handleTokenExpiredException(TokenExpiredException e) {
        return ResponseEntity.ok(new TokenExpiredExceptionResponse());
    }

    @ExceptionHandler(value = EmailNotFoundException.class)
    public ResponseEntity<?> handleEmailNotFoundException(EmailNotFoundException e) {
        return ResponseEntity.ok(new EmailNotFoundExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(value = UserIdNotNullException.class)
    public ResponseEntity<?> handleUserIdNotNullException(UserIdNotNullException e) {
        return ResponseEntity.ok(new UserIdNotNullExceptionResponse());
    }

    @ExceptionHandler(value = AuthorizationHeaderException.class)
    public ResponseEntity<?> handleUserIdNotNullException(AuthorizationHeaderException e) {
        return ResponseEntity.ok(new AuthorizationHeaderExceptionResponse());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationExceptionn(AuthenticationException e) {
        return ResponseEntity.ok(new AuthenticationExceptionResponse());
    }

    @ExceptionHandler(value = AuthenticationRefreshTokenExpiredException.class)
    public ResponseEntity<?> handleAuthenticationRefreshTokenExpiredException(AuthenticationRefreshTokenExpiredException e) {
        return ResponseEntity.ok(new AuthenticationRefreshTokenExpiredExceptionResponse());
    }

    @ExceptionHandler(value = AuthenticationRefreshTokenNotExistsException.class)
    public ResponseEntity<?> handleAuthenticationRefreshTokenNotExistsException(AuthenticationRefreshTokenNotExistsException e) {
        return ResponseEntity.ok(new AuthenticationRefreshTokenNotExistsExceptionResponse());
    }

    @ExceptionHandler(value = JWTDecodeException.class)
    public ResponseEntity<?> handleAuthenticationRefreshTokenNotExistsException(JWTDecodeException e) {
        return ResponseEntity.ok(new JWTDecodeExceptionResponse());
    }
}
