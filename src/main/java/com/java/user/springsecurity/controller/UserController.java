package com.java.user.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class UserController {


    @GetMapping("/role-admin")
    public String getAdmin(){
        return "admin";
    }

    @GetMapping("/role-user")
    public String getUser(){
        return "user";
    }
}
