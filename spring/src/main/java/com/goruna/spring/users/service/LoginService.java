package com.goruna.spring.users.service;

import com.goruna.spring.security.util.JwtUtil;
import com.goruna.spring.users.dto.UserRequestDto;
import com.goruna.spring.users.entity.User;
import com.goruna.spring.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    @Transactional
    public String socialLogin(String userEmail) {
        Optional<User> user = userRepository.findByUserEmail(userEmail);

        if (!user.isPresent()) {
            UserRequestDto userRequestDto = new UserRequestDto();
            userRequestDto.setUserEmail(userEmail);
            userRequestDto.setUserNickname("unKnown");

            User newUser = modelMapper.map(userRequestDto, User.class);
            userRepository.save(newUser);
            return "회원가입 완료: " + userEmail;
        } else {
            String token = jwtUtil.generateToken(user.get().getUserSeq(), userEmail);
            return "로그인 성공: " + userEmail + ", Token: " + token;
        }
    }
}
