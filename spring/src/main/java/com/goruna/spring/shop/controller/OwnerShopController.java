package com.goruna.spring.shop.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.shop.dto.UpdateShopInfoDTO;
import com.goruna.spring.shop.service.OwnerShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
@Tag(name = "", description = "사장용 api")    // name 설정 해야함
public class OwnerShopController {

    private final OwnerShopService ownerShopService;

    @PutMapping("/shop/{shopSeq}")
    @Operation(summary = "(사장) 매장 정보 수정", description = "(사장) 매장 정보를 수정합니다.")
    public ApiResponse<?> updateShopInfo(@PathVariable Long shopSeq, @RequestBody UpdateShopInfoDTO updateShopInfoDTO) {
        ownerShopService.updateShopInfo(shopSeq, updateShopInfoDTO);
        return ResponseUtil.successResponse("데이터가 성공적으로 수정되었습니다").getBody();
    }

}
