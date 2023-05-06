package com.backend.tradeappbackend.jwt;

public interface JWTDecoder {
    public DecodedAuthorizationDetails decode(String token);
}
