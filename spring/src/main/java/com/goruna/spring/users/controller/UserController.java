package com.goruna.spring.users.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    @PostMapping("/join")
    @Operation(summary = "회원가입" , description = "회원가입 로직 입니다.")
    public ApiResponse<?> createUser(@RequestBody SignUpRequestDTO signUpRequestDTO){
        SignUpRequestDTO createUser = userService.createUser(signUpRequestDTO);
        return ResponseUtil.successResponse("회원가입이 성공적으로 되었습니다.").getBody();
    }
}
