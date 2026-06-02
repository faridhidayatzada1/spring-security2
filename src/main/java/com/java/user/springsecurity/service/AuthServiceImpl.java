package com.java.user.springsecurity.service;

import com.java.user.springsecurity.dto.AccessToken;
import com.java.user.springsecurity.dto.SignResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService1 jwtService1;
    private final AuthenticationManager authenticationManager;

    @Override
    public SignResponse signIn(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        final String token = jwtService1.issueToken(authentication);
        AccessToken accessToken = new AccessToken(token);
        return new SignResponse("BEARER", accessToken);
    }
}
