package com.java.user.springsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.user.springsecurity.dto.UserDto;
import com.java.user.springsecurity.service.JwtService1;
import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthRequestFilter extends OncePerRequestFilter {


    private final JwtService1 jwtService1;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Swagger UI və API docs bypass
        if(path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<Authentication> authenticationOptional = authentication(request.getHeader("Authorization"));
        authenticationOptional.ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));

        log.info("JWT Auth filter executed, authentication: {}", authenticationOptional);

        filterChain.doFilter(request,response);
    }

    private Optional<Authentication> authentication(String authHeader) {
        final Optional<String> bearer = getBearerToken(authHeader);
        if(bearer.isPresent()) {
            Claims claims = jwtService1.parseToken(bearer.get());
            Collection<? extends GrantedAuthority> authorities = getAuthorities(claims);
            UserDetails userDetails = objectMapper.convertValue(claims.get("principal"), UserDto.class);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, bearer.get(), authorities);
            log.info("Principal class: {}", authToken.getPrincipal().getClass());
            log.info("Principal value: {}", authToken.getPrincipal());

            return Optional.of(authToken);
        }
        return Optional.empty();
    }

    private Optional<String> getBearerToken(String header) {
        if(header == null || !header.startsWith("Bearer ")) {
            return Optional.empty();
        }
        return Optional.of(header.replaceFirst("Bearer ", "").trim());
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
        List<String> roles = claims.get("roles", List.class);
        return roles.stream().map(SimpleGrantedAuthority::new).toList();
    }
}

