package com.goruna.spring.users.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.common.util.CustomUserUtils;
import com.goruna.spring.users.dto.NickNameRequestDto;
import com.goruna.spring.users.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Info API", description = "user info API")
public class UserController {

    private final UserInfoService userInfoService;

    @PostMapping("/nickname")
    @Operation(summary = "회원 닉네임 추가", description = "회원의 닉네임을 추가합니다.")
    public ApiResponse<?> addNickname(@RequestBody NickNameRequestDto nickNameRequestDto) {
        Long userSeq = CustomUserUtils.getCurrentUserSeq();
        System.out.println(userSeq+"userSeq");
        userInfoService.createNickname(userSeq, nickNameRequestDto);
        return ResponseUtil.successResponse("회원 닉네임이 성공적으로 추가되었습니다").getBody();
    }
}
