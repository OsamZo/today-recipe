package com.goruna.spring.security.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class CustomUserDetails extends User{

    public CustomUserDetails(String email, String passoword, List<GrantedAuthority> grantedAuthorities) {
        super(email, passoword, grantedAuthorities);
    }
}
