package com.backend.tradeappbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:datasource.properties")
public class DatasourceConfig {
    @Value("${sql.username}")
    private String username;

    @Value("${sql.password}")
    private String password;

    @Value("${sql.url}")
    private String url;

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .password(password)
                .username(username)
                .url(url)
                .build();
    }

}
