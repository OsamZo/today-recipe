package com.goruna.spring.shop.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.shop.dto.AdminShopResponseDTO;
import com.goruna.spring.shop.service.AdminShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/shop")
@Tag(name = "admin", description = "admin 매장 전체 조회")
public class AdminShopController {

    private final AdminShopService adminSearchService;

    // 매장 데이터 전체 조회
    @GetMapping
    @Operation(summary = "매장 데이터 전체 조회", description = "매장 데이터 전체를 조회합니다.")
    public ApiResponse<?> getAdminAllShop(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
        List<AdminShopResponseDTO> allShops = adminSearchService.getAdminAllSearch(page, size);
        return ResponseUtil.successResponse("전체 매장 데이터가 성공적으로 조회되었습니다.", allShops).getBody();
    }

}
