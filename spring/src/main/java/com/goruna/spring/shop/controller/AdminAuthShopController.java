package com.goruna.spring.shop.controller;


import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.shop.dto.AdminAuthShopDetailResponseDTO;
import com.goruna.spring.shop.dto.AdminAuthShopResponseDTO;
import com.goruna.spring.shop.service.AdminAuthShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/")
@Tag(name = "admin", description = "admin 매장 전체 조회")
public class AdminAuthShopController {

    private final AdminAuthShopService adminAuthSearchService;

    // 매장 등록 인증 전체 조회
    @GetMapping("/shop/auth")
    @Operation(summary = "매장 등록 인증 전체 조회", description = "매장 데이터 전체를 조회합니다.")
    public ApiResponse<?> getAdminAuthAllShop(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        List<AdminAuthShopResponseDTO> allAuthShops = adminAuthSearchService.getAdminAuthAllShop(page, size);
        return ResponseUtil.successResponse("전체 매장 등록 인증 데이터가 성공적으로 조회되었습니다.", allAuthShops).getBody();
    }

    // 매장 등록 인증 상세 조회
    @GetMapping("/shop/{shopSeq}/auth")
    @Operation(summary = "매장 등록 인증 상세 조회")
    public ApiResponse<?> getAdminAuthShopDetail(@PathVariable Long shopSeq){
        AdminAuthShopDetailResponseDTO AuthShopDetail = adminAuthSearchService.getAdminAuthShopDetail(shopSeq);
        return ResponseUtil.successResponse("매장 등록 인증 상세 데이터가 성공적으로 조회되었습니다.", AuthShopDetail).getBody();

    }
}
