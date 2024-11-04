package com.goruna.spring.shop.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.shop.dto.ShopListReadResDTO;
import com.goruna.spring.shop.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "shop", description="매장 API")
@RestController
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/{categorySeq}/shop")
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
}
