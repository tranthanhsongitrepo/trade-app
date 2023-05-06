package com.backend.tradeappbackend.staff;

import com.backend.tradeappbackend.user.exception.EmailExistsException;
import com.backend.tradeappbackend.user.response.UsernameExistsExceptionResponse;
import com.backend.tradeappbackend.user.response.UsernameNotFoundExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StaffExceptionHandler {
    @ExceptionHandler(value = EmailExistsException.class)
    public ResponseEntity<?> handleUsernameExistsException(EmailExistsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new UsernameExistsExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new UsernameNotFoundExceptionResponse(e.getMessage()));
    }

}
