package com.goruna.spring.shop.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.shop.dto.ShopDetailReadResDTO;
import com.goruna.spring.shop.dto.ShopListReadResDTO;
import com.goruna.spring.shop.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "shop", description="매장 API")
@RestController
@RequestMapping("/api/v1/category/{categorySeq}/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    @Operation(
            summary = "카테고리 별 매장 목록 데이터 조회",
            description = "카테고리 별 매장 목록 데이터를 조회합니다."
    )
    public ApiResponse<?> readShopsByCategory(
            @PathVariable(value = "categorySeq") Long categorySeq,
            @RequestParam(defaultValue = "1") Integer page
    ) {
        List<ShopListReadResDTO> shopListReadResDTOS = shopService.readShopsByCategory(categorySeq, page);
        return ResponseUtil.successResponse("카테고리 별 매장 목록 데이터가 성공적으로 조회되었습니다.", shopListReadResDTOS).getBody();
    }

    // 매장 상세 정보 조회
    @GetMapping("/{shopSeq}")
    @Operation(
            summary = "매장 상세 데이터 조회",
            description = "매장 상세 데이터를 조회합니다."
    )
    public ApiResponse<?> readShopsByCategoryAndShop(
            @PathVariable(value = "categorySeq") Long categorySeq,
            @PathVariable(value = "shopSeq") Long shopSeq
    ) {
        ShopDetailReadResDTO shopDetailReadResDTO = shopService.readShopDetail(shopSeq);
        return ResponseUtil.successResponse("매장 상세 데이터가 성공적으로 조회되었습니다.", shopDetailReadResDTO).getBody();
    }

}
