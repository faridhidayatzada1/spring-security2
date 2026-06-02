package com.java.user.springsecurity.controller;

import com.java.user.springsecurity.dto.SignResponse;
import com.java.user.springsecurity.service.AuthService;
import com.java.user.springsecurity.service.JwtService1;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService1 jwtService1;
    private final AuthService authService;

    @GetMapping
    public String issueToken(Authentication authentication) {
        return jwtService1.issueToken(authentication);
    }

    @GetMapping("/jwt-test1")
    public ResponseEntity<SignResponse> issueTokenTest(@RequestParam String username,
                                                       @RequestParam String password){

        SignResponse signResponse = authService.signIn(username, password);
        HttpHeaders headers = new HttpHeaders();
    //    setCookies(headers,signResponse);
        return new ResponseEntity<>(signResponse,headers, HttpStatus.OK);
    }
}
