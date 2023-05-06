package com.backend.tradeappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TradeAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeAppBackendApplication.class, args);
    }

}
