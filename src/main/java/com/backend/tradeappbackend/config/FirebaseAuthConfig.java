package com.backend.tradeappbackend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseAuthConfig {
    @Bean
    public FirebaseAuth firebaseAuth() throws IOException {

//        FileInputStream serviceAccount =
//                new FileInputStream("firebase-admin-sdk.json");
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("firebase-admin-sdk.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();


        FirebaseApp.initializeApp(options);
        return FirebaseAuth.getInstance();
    }
}
