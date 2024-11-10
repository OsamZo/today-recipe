package com.goruna.spring.review.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.review.dto.GoodRequestDTO;
import com.goruna.spring.review.dto.GoodResDTO;
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

    /* 좋아요 상태 조회 */
    @GetMapping("/good")
    @Operation(summary = "좋아요 상태 조회 API", description = "좋아요 상태 조회 위한 API 입니다.")
    public ApiResponse<?> readGood(@PathVariable Long shopSeq, @RequestParam Long userSeq, @RequestParam Long reviewSeq) {
        GoodResDTO responseDTO = goodService.readGood(userSeq, reviewSeq);
        return ResponseUtil.successResponse("좋아요 조회 성공", responseDTO).getBody();
    }

    /* 좋아요 추가 */
    @PostMapping("/good")
    @Operation(summary = "좋아요 추가 API", description = "좋아요 추가를 위한 API 입니다.")
    public ApiResponse<?> addGood(@PathVariable Long shopSeq, @RequestBody GoodRequestDTO requestDTO) {

        GoodResDTO resDTO = goodService.createGood(requestDTO);
        return ResponseUtil.successResponse("좋아요가 성공적으로 추가되었습니다.", resDTO).getBody();
    }

    /* 좋아요 삭제 */
    @DeleteMapping("/good/{goodSeq}")
    @Operation(summary = "좋아요 삭제 API", description = "좋아요 삭제를 위한 API 입니다.")
    public ApiResponse<?> deleteGood(@PathVariable Long shopSeq, @PathVariable Long goodSeq, @RequestParam Long userSeq) {
        boolean isDeleted = goodService.deleteGood(goodSeq, userSeq);
        if (isDeleted) {
            return ResponseUtil.successResponse("좋아요가 성공적으로 삭제되었습니다.").getBody();
        } else {
            return ResponseUtil.failureResponse("좋아요 삭제 실패: 권한이 없습니다.", null).getBody();
        }
    }
}
