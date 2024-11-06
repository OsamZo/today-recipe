package com.goruna.spring.shop.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.shop.dto.UserShopStatusResponse;
import com.goruna.spring.shop.service.UserShopStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Info API", description = "회원 매장 신청 상태")
public class UserShopStatusController {

    private final UserShopStatusService userShopStatusService;

    @GetMapping("/{userSeq}/info/shop")
    @Operation(summary = "회원 매장 신청 상태", description = "회원 매장 신청 상태를 조회합니다.")
    public ApiResponse<?> getUserShopStatus(@PathVariable("userSeq") String userSeq) {
        UserShopStatusResponse userShopStatusResponse = userShopStatusService.getUserShopStatus(userSeq);
        return ResponseUtil.successResponse("회원 매장 상태가 조회되었습니다.",userShopStatusResponse).getBody();
    }
}
