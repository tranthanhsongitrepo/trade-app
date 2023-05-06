package com.backend.tradeappbackend.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.firebase.auth.FirebaseToken;
import lombok.Data;

@Data
public class DecodedAuthorizationDetails {
    private String email;
    private String provider;
    private String loggedInDeviceId;

    public DecodedAuthorizationDetails(DecodedJWT decodedJWT) {
        this.email = decodedJWT.getSubject();
        this.provider = decodedJWT.getIssuer();
        this.loggedInDeviceId = decodedJWT.getClaim("loggedInDeviceId").asString();
    }
}
