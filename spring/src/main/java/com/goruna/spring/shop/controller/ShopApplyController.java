package com.goruna.spring.shop.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.shop.dto.ShopApplyReqDTO;
import com.goruna.spring.shop.service.ShopApplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user/{userSeq}/info/shop")
@RequiredArgsConstructor
@Tag(name = "Shop API", description = "매장 등록 신청 API")
public class ShopApplyController {

    private final ShopApplyService shopApplyService;

    @PostMapping
    @Operation(summary = "매장 등록 신청 API (일반 유저가)", description = "매장을 등록하는 API입니다.")
    public ApiResponse<?> registerShop(@PathVariable Long userSeq, @RequestBody ShopApplyReqDTO shopApplyReqDTO) {
        shopApplyService.registerShop(userSeq, shopApplyReqDTO);
        return ResponseUtil.successResponse("매장이 성공적으로 등록되었습니다.", null).getBody();
    }
}
