package com.goruna.spring.shop.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.shop.dto.AdminCategoryRequestDTO;
import com.goruna.spring.shop.service.AdminCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/category")
@Tag(name = "admin", description = "admin 카테고리 API")
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @PostMapping()
    @Operation(summary = "카테고리 추가", description = "관리자 권한으로 카테고리를 추가합니다.")
    public ApiResponse<?> createCategory(@Valid @RequestBody AdminCategoryRequestDTO adminCategoryRequestDTO) {

        AdminCategoryRequestDTO createdCategory = adminCategoryService.createCategory(adminCategoryRequestDTO);

        return ResponseUtil.successResponse("카테고리 등록이 성공적으로 완료되었습니다.", createdCategory).getBody();


    }
}
