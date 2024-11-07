package com.goruna.spring.review.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.review.dto.GoodDeleteResDTO;
import com.goruna.spring.review.dto.GoodRequestDTO;
import com.goruna.spring.review.dto.GoodCreateResDTO;
import com.goruna.spring.review.service.GoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shop/{shopSeq}/review")
@RequiredArgsConstructor
@Tag(name = "Good API", description = "좋아요 추가, 삭제 API")
public class GoodController {

    private final GoodService goodService;

    /* 좋아요 추가 */
    @PostMapping("/good")
    @Operation(summary = "좋아요 추가 API", description = "좋아요 추가를 위한 API 입니다.")
    public ApiResponse<?> addGood(@PathVariable Long shopSeq, @RequestBody GoodRequestDTO requestDTO) {
        GoodCreateResDTO responseDTO = goodService.createGood(requestDTO);
        return ResponseUtil.successResponse("좋아요가 성공적으로 추가되었습니다.", responseDTO).getBody();
    }

    /* 좋아요 삭제 */
    @DeleteMapping("/good/{goodSeq}")
    @Operation(summary = "좋아요 삭제 API", description = "좋아요 삭제를 위한 API 입니다.")
    public ApiResponse<?> deleteGood(@PathVariable Long shopSeq, @PathVariable Long goodSeq) {
        GoodDeleteResDTO goodDeleteResDTO = goodService.deleteGood(goodSeq);
        return ResponseUtil.successResponse("좋아요가 성공적으로 삭제되었습니다.", null).getBody();
    }
}
