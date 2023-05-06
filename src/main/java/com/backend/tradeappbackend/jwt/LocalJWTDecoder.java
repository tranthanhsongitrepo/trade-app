package com.backend.tradeappbackend.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class LocalJWTDecoder implements JWTDecoder {
    private final String secret;

    public LocalJWTDecoder(String secret) {
        this.secret = secret;
    }

    public DecodedAuthorizationDetails decode(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        return new DecodedAuthorizationDetails(decodedJWT);
    }

}
