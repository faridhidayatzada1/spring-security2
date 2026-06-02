package com.java.user.springsecurity.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    // Əsas filter metodumuz:
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Əgər token yoxdursa və ya Bearer ilə başlamırsa, filteri keç və növbəti addıma ötür!
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // Mütləq return olmalıdır ki, aşağıdakı kodlar işləməsin!
        }

        // ... (token yoxlama kodları) ...
        filterChain.doFilter(request, response);
    }


}
