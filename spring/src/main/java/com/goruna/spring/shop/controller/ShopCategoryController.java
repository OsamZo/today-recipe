package com.goruna.spring.shop.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.shop.dto.ShopCategoryReadResDTO;
import com.goruna.spring.shop.service.ShopCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "shop-category", description = "매장 카테고리 API")
@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class ShopCategoryController {

    private final ShopCategoryService shopCategoryService;

    @GetMapping
    @Operation(summary = "매장 카테고리 데이터 전체 조회", description = "매장 카테고리 데이터 전체를 조회합니다.")
    public ApiResponse<?> readAllCategories() {
        List<ShopCategoryReadResDTO> shopCategoryReadResDTO = shopCategoryService.readAllCategories();
        return ResponseUtil.successResponse("매장 카테고리 전체 데이터가 성공적으로 조회되었습니다", shopCategoryReadResDTO).getBody();
    }
}