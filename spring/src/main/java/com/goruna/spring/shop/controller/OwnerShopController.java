package com.goruna.spring.shop.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.shop.dto.CreateProductReqDTO;
import com.goruna.spring.shop.dto.OnwerShopInfoResDTO;
import com.goruna.spring.shop.dto.UpdateShopInfoDTO;
import com.goruna.spring.shop.service.OwnerShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
@Tag(name = "OwnerShop API", description = "사장용 매장 api")
public class OwnerShopController {

    private final OwnerShopService ownerShopService;

    @PutMapping("/shop/{shopSeq}")
    @Operation(summary = "(사장) 매장 정보 수정", description = "(사장) 매장 정보를 수정합니다.")
    public ApiResponse<?> updateShopInfo(@PathVariable Long shopSeq, @RequestBody UpdateShopInfoDTO updateShopInfoDTO) {
        ownerShopService.updateShopInfo(shopSeq, updateShopInfoDTO);
        return ResponseUtil.successResponse("데이터가 성공적으로 수정되었습니다").getBody();
    }

    @DeleteMapping("/shop/{shopSeq}")
    @Operation(summary = "(사장) 매장 정보 삭제", description = "(사장) 매장 정보를 삭제합니다.")
    public ApiResponse<?> deleteShop(@PathVariable Long shopSeq) {
        ownerShopService.deleteShop(shopSeq);
        return ResponseUtil.successResponse("데이터가 성공적으로 삭제되었습니다.").getBody();
    }

    @GetMapping("/shop")
    @Operation(summary = "(사장) 내 매장 정보 조회", description = "(사장) 나의 매장 정보를 조회합니다.")
    public ApiResponse<?> getOwnerShopInfo() {
        OnwerShopInfoResDTO ownerShopInfo = ownerShopService.getOnwerShopInfo();
        return ResponseUtil.successResponse("데이터를 성공적으로 조회하였습니다.", ownerShopInfo).getBody();
    }

    @PostMapping("/shop/{shopSeq}/product")
    @Operation(summary = "(사장) 상품 등록", description = "(사장) 상품을 등록합니다.")
    public ApiResponse<?> createProduct(
            @PathVariable Long shopSeq,
            @RequestBody CreateProductReqDTO createProductReqDTO) {
        ownerShopService.createProductInfo(shopSeq, createProductReqDTO);
        return ResponseUtil.successResponse("데이터를 성공적으로 등록하였습니다.").getBody();
    }

}
