package com.goruna.spring.shop.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.shop.dto.ShopCardReadResDTO;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    // 오늘의 특가 리스트 조회
    @GetMapping("/shop/today")
    @Operation(
            summary = "오늘의 특가 리스트 조회(최신순 5개)",
            description = "오늘의 특가 리스트 데이터를 조회합니다."
    )
    public ApiResponse<?> readshopsToday() {
        List<ShopListReadResDTO> shopListReadResDTOS = shopService.readLatest5ShopsToday();
        return ResponseUtil.successResponse("오늘의 특가 리스트 데이터가 성공적으로 조회되었습니다.", shopListReadResDTOS).getBody();
    }

    // 전체 매장 리스트 조회
    @GetMapping("/shop")
    @Operation(
            summary = "(오늘 등록된 상품이 있는)전체 매장 목록 데이터 조회",
            description = "(오늘 등록된 상품이 있는)전체 매장 목록 데이터를 조회합니다."
    )
    public ApiResponse<?> readShopsAlToday() {
        List<ShopListReadResDTO> shopListReadResDTOS = shopService.readShopsAllToday();
        return ResponseUtil.successResponse("카테고리 별 매장 목록 데이터가 성공적으로 조회되었습니다.", shopListReadResDTOS).getBody();
    }

    // 카테고리별 매장 목록 조회
    @GetMapping("/category/{categorySeq}/shop")
    @Operation(
            summary = "카테고리별 매장 목록 데이터 조회",
            description = "카테고리별 매장 목록 데이터를 조회합니다."
    )
    public ApiResponse<?> readShopsByCategory(
            @PathVariable(value = "categorySeq") Long categorySeq,
            @RequestParam(defaultValue = "1") Integer page
    ) {
        List<ShopListReadResDTO> shopListReadResDTOS = shopService.readShopsByCategory(categorySeq, page);

        return ResponseUtil.successResponse("카테고리 별 매장 목록 데이터가 성공적으로 조회되었습니다.", shopListReadResDTOS).getBody();
    }

    // 매장 상세 정보 조회
    @GetMapping("/category/{categorySeq}/shop/{shopSeq}")
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

    /* 매장 이미지, 매장 이름 조회 API */
    @GetMapping("/shop/{shopSeq}")
    @Operation(
            summary = "매장 이미지, 매장 이름 조회 API",
            description = "매장 이미지, 매장 이름을 조회합니다."
    )
    public ApiResponse<?> readShopImgAndName(@PathVariable Long shopSeq){

        ShopCardReadResDTO shopCardReadResDTO = shopService.shopCardRead(shopSeq);
        return ResponseUtil.successResponse("매장 카드 데이터가 성공적으로 조회되었습니다.", shopCardReadResDTO).getBody();
    }
}
