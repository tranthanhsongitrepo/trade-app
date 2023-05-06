package com.backend.tradeappbackend.user.response;

import com.backend.tradeappbackend.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UsernameExistsExceptionResponse extends GenericResponse {
    private String message;

    public UsernameExistsExceptionResponse(String message) {
        super(400, message);
        this.message = message;
    }
}
