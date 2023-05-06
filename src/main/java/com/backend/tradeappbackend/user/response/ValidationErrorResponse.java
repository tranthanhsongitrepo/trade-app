package com.backend.tradeappbackend.user.response;

import com.backend.tradeappbackend.response.GenericResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValidationErrorResponse extends GenericResponse {
    private final String field;

    public ValidationErrorResponse(String field, String message) {
        this.field = field;
        this.setMessage(message);
    }
}
