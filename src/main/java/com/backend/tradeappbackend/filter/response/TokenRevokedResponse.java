package com.backend.tradeappbackend.filter.response;

import com.backend.tradeappbackend.response.GenericResponse;

public class TokenRevokedResponse extends GenericResponse {
    public TokenRevokedResponse() {
        super(403, "Token revoked");
    }
}
