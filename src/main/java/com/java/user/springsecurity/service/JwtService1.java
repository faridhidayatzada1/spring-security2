package com.java.user.springsecurity.service;

import com.java.user.springsecurity.config.JwtTokenConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtService1 {

    private final JwtTokenConfigProperties tokenConfigProperties;

    private SecretKey key;

    @PostConstruct
    public void init() {
        if (StringUtils.isBlank(tokenConfigProperties.getJwtProperties().getSecret())) {
            throw new RuntimeException("Token config not initialized");
        }
        byte[] keyBytes = tokenConfigProperties.getJwtProperties().getSecret().getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .clockSkewSeconds(600)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String issueToken(Authentication authentication) {
        try {
            // 1. Rolları obyekt yox, sırf String siyahısı (List<String>) olaraq çıxarırıq
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            final JwtBuilder jwts = Jwts.builder()
                    .subject(authentication.getName())
                    .issuedAt(new Date())
                    .expiration(Date.from(Instant.now().plus(Duration.ofSeconds(tokenConfigProperties.getJwtProperties().getTokenValidateTokens()))))
                    .header().add(Map.of("type","JWT")).and()
                    .signWith(key)
                    // 2. Mürəkkəb obyekt əvəzinə təmiz String siyahısını tokenə əlavə edirik
                    .claim("authorities", roles);

            return jwts.compact();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
