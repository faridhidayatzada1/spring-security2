package com.java.user.springsecurity;

import com.java.user.springsecurity.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class Main {

    static JwtService jwtService = new JwtService();


    public static void main(String[] args) throws Exception {
        Main main = new Main();
        jwtService.init();
        main.parseToken();

    }

    public void parseToken() throws Exception {
        Claims claims = jwtService.parseToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30");
        System.out.println("claims: " + claims);
        String jwt = jwtService.issueToken();
        System.out.println("JWT: " + jwt);
    }
}
