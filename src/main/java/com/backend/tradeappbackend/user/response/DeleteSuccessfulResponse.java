package com.backend.tradeappbackend.user.response;

import com.backend.tradeappbackend.response.GenericResponse;

public class DeleteSuccessfulResponse extends GenericResponse {
    public DeleteSuccessfulResponse() {
        super(200, "Deleted user successfully");
    }

}
