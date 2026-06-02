package com.java.user.springsecurity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JwtService {

    private Key key;
    private String secret = "a-string-secret-at-least-256-bits-long";

    @PostConstruct
    public void init() {
        // secret dəyəri base64 və ya sad string ola bilər
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String issueToken() {
         return Jwts.builder()
                .subject("Farid")
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(Duration.ofMillis(30_000))))
                .header().add(Map.of("type","JWT")).and()
                 .addClaims(Map.of("roles", List.of("ROLE_USER")))
                .signWith(key)
                 .claims(Map.of("authorities","HS256"))
                 .compact();
    }
}
