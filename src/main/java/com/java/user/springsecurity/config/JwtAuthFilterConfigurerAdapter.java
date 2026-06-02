package com.java.user.springsecurity.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtAuthFilterConfigurerAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtAuthRequestFilter  jwtAuthRequestFilter;

    @Override
    public void configure(HttpSecurity http)  {
        log.info("Configuring JwtAuthFilterConfigurerAdapter");
        http.addFilterBefore(jwtAuthRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
