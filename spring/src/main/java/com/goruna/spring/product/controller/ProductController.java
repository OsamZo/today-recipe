package com.goruna.spring.product.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.product.service.ProductService;
import com.goruna.spring.product.dto.CreateProductReqDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
@Tag(name = "OwnerProduct API", description = "사장용 상품 api")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    @Operation(summary = "(사장) 상품 등록", description = "(사장) 상품을 등록합니다.")
    public ApiResponse<?> createProduct(@RequestBody CreateProductReqDTO createProductReqDTO) {
        productService.createProductInfo(createProductReqDTO);
        return ResponseUtil.successResponse("데이터를 성공적으로 등록하였습니다.").getBody();
    }
}
