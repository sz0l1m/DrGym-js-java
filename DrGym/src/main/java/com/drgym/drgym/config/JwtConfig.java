package com.drgym.drgym.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
public class JwtConfig {
    private static final String key = "Two4LFp3YVszbmKc7Nf731/4SePEr4awUwaahhX2YBY=";
    @Bean
    public Key jwtSecretKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }
}