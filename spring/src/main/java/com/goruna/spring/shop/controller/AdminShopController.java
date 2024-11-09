package com.goruna.spring.shop.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.shop.dto.AdminShopAuthImgDTO;
import com.goruna.spring.shop.dto.AdminShopResponseDTO;
import com.goruna.spring.shop.dto.AdminShopSearchResponseDTO;
import com.goruna.spring.shop.service.AdminShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/shop")
@Tag(name = "admin", description = "admin 매장 전체 조회")
public class AdminShopController {

    private final AdminShopService adminShopService;

    // 매장 데이터 전체 조회
    @GetMapping
    @Operation(summary = "매장 데이터 전체 조회", description = "매장 데이터 전체를 조회합니다.")
    public ApiResponse<?> getAdminAllShop(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size){
        List<AdminShopResponseDTO> allShops = adminShopService.getAdminAllSearch(page, size);
        return ResponseUtil.successResponse("전체 매장 데이터가 성공적으로 조회되었습니다.", allShops).getBody();
    }

    // 관리자 매장 검색
    @GetMapping("/search")
    @Operation(summary = "관리자 매장 데이터 조회", description = "매장 데이터를 검색합니다.")
    public ApiResponse<?> getAdminShop(
            @RequestParam String shopName,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        List<AdminShopSearchResponseDTO> getShops = adminShopService.getAdminShop(shopName, page, size);
        return ResponseUtil.successResponse("검색한 매장 데이터가 성공적으로 조회되었습니다.", getShops).getBody();

    }

    // 매장 삭제
    @DeleteMapping("/{shopSeq}")
    @Operation(summary = "관리자 매장 삭제", description = "매장 데이터를 삭제합니다.")
    public ApiResponse<?> deleteShop(
            @PathVariable Long shopSeq){

        adminShopService.deleteShop(shopSeq);
        return ResponseUtil.successResponse("등록된 메장 삭제가 성공적으로 진행되었습니다.", null).getBody();
    }

    // 사업자 등록증 조회
    @GetMapping("/{shopSeq}/auth/img")
    @Operation(summary = "사업자 등록증 조회", description = "등록된 사업자 등록증을 조회합니다.")
    public ApiResponse<?> getAdminAllShop(
            @PathVariable Long shopSeq){
        AdminShopAuthImgDTO shopAuthImg = adminShopService.getShopAuthImg(shopSeq);
        return ResponseUtil.successResponse("등록된 사업자 등록증이 성공적으로 조회되었습니다.", shopAuthImg).getBody();
    }



}
