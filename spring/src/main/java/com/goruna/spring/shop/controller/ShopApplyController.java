package com.goruna.spring.shop.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.common.s3.FileUploadUtil;
import com.goruna.spring.shop.dto.ShopApplyReqDTO;
import com.goruna.spring.shop.service.ShopApplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/user/{userSeq}/info/shop")
@RequiredArgsConstructor
@Tag(name = "Shop API", description = "매장 등록 신청 API")
public class ShopApplyController {

    private final ShopApplyService shopApplyService;
    private final FileUploadUtil fileUploadUtil;

    @PostMapping(value = "/{categorySeq}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "매장 등록 신청 API (일반 유저가)", description = "매장을 등록하는 API입니다.")
    public ApiResponse<?> registerShop(@PathVariable Long userSeq,
                                       @PathVariable Long categorySeq,
                                       @RequestPart("shopApplyReqDTO") ShopApplyReqDTO shopApplyReqDTO,
                                       @RequestPart("shopBusinessImgUrl") MultipartFile shopBusinessImgUrl,
                                       @RequestPart("shopImgUrl") MultipartFile shopImgUrl) {
        try {
            String businessImg = fileUploadUtil.uploadFile(shopBusinessImgUrl);
            String shopImg = fileUploadUtil.uploadFile(shopImgUrl);

            shopApplyService.registerShop(userSeq, categorySeq, shopApplyReqDTO, businessImg, shopImg);

        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 중 오류 발생", e);
        }

        // 매장 등록 처리 완료 응답
        return ResponseUtil.successResponse("매장이 성공적으로 등록되었습니다.", null).getBody();
    }
}
