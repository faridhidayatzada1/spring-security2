package com.java.user.springsecurity.controller;

import com.java.user.springsecurity.service.JwtService1;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService1 jwtService1;

    @GetMapping
    public String issueToken(Authentication authentication) {
        return jwtService1.issueToken(authentication);
    }
}
