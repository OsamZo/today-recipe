package com.goruna.spring.security.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class CustomUserDetails extends User{

    private final Long userSeq;

    public CustomUserDetails(Long userSeq, String email, String passoword, List<GrantedAuthority> grantedAuthorities) {
        super(email, passoword, grantedAuthorities);
        this.userSeq=userSeq;
    }
    public Long getUserSeq() {
        return userSeq;
    }
}
