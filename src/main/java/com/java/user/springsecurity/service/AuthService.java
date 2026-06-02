package com.java.user.springsecurity.service;

import com.java.user.springsecurity.dto.SignResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    SignResponse signIn(String username, String password);

}
