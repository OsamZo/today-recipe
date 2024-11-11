package com.goruna.spring.product.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.common.s3.FileUploadUtil;
import com.goruna.spring.product.dto.CurrentProductResDTO;
import com.goruna.spring.product.service.ProductService;
import com.goruna.spring.product.dto.CreateProductReqDTO;
import com.goruna.spring.shop.dto.ShopApplyReqDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
@Tag(name = "OwnerProduct API", description = "사장용 상품 api")
public class ProductController {

    private final ProductService productService;
    private final FileUploadUtil fileUploadUtil;

    @PostMapping(value = "/product/{userSeq}",
                    consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "(사장) 상품 등록", description = "(사장) 상품을 등록합니다.")
    public ApiResponse<?> createProduct(@RequestPart("createProductReqDTO") CreateProductReqDTO createProductReqDTO,
                                        @RequestPart("productImgUrl") MultipartFile productImgUrl,
                                        @PathVariable Long userSeq) {

        try {
            String productImg = fileUploadUtil.uploadFile(productImgUrl);
            productService.createProductInfo(createProductReqDTO, productImg, userSeq);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 중 오류 발생", e);
        }

        return ResponseUtil.successResponse("데이터를 성공적으로 등록하였습니다.").getBody();
    }

    @GetMapping("/product")
    @Operation(summary = "(사장) 상품 정보 조회", description = "(사장) 상품 정보를 조회합니다.")
    public ApiResponse<?> getCurrentProduct() {
        CurrentProductResDTO currentProductResDTO = productService.getCurrentProduct();
        return ResponseUtil.successResponse("데이터를 성공적으로 조회하였습니다.", currentProductResDTO).getBody();
    }
}
