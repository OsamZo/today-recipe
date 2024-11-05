package com.goruna.spring.users.controller;

import com.goruna.spring.users.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@RequiredArgsConstructor
public class UserController {

    private final LoginService loginService;

    @GetMapping("/code/{registrationId}")
    @Operation(summary = "회원가입" , description = "회원가입 로직 입니다.")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationId) {
        loginService.socialLogin(code, registrationId);
    }
}
