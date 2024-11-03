package com.goruna.spring.users.service;

import com.goruna.spring.users.dto.SignUpRequestDTO;
import com.goruna.spring.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public SignUpRequestDTO createUser(SignUpRequestDTO signUpRequestDTO) {
        User user = modelMapper.map(SignUpRequestDTO, User.class);
        userRepository.save(user);
        return signUpRequestDTO;
    }
}
