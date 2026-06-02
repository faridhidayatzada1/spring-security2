package com.java.user.springsecurity.dto;


import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
public class UserDto implements UserDetails {


    private Long id;

    private List<UserAuthorityDto> authorities;

    private String  password;

    private String  username;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
}

