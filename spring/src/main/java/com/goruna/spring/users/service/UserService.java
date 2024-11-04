package com.goruna.spring.users.service;

import com.goruna.spring.users.dto.SignUpRequestDTO;

import com.goruna.spring.users.entity.User;
import com.goruna.spring.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public SignUpRequestDTO createUser(SignUpRequestDTO signUpRequestDTO) {
        User user = modelMapper.map(signUpRequestDTO, User.class);
        userRepository.save(user);
        return signUpRequestDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        User loginUser = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException(userEmail));

        System.out.println(loginUser);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(loginUser.getUserRole().name()));
        return new org.springframework.security.core.userdetails.User(loginUser.getUserEmail(), "{noop}1", grantedAuthorities);
    }

}
