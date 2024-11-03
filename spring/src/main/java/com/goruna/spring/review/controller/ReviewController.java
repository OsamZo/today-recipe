package com.goruna.spring.review.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.review.dto.ReviewCreateRequestDTO;
import com.goruna.spring.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Review API", description = "Review 추가, 조회 API")
public class ReviewController {

    private final ReviewService reviewService;

    /* 리뷰 등록 API */
    @PostMapping("/user/{userSeq}/book/{bookSeq}")
    @Operation(summary = "리뷰 등록 API", description = "리뷰 등록을 위한 API 입니다.")
    public ApiResponse<?> createReview(
            @PathVariable Long userSeq,
            @PathVariable Long bookSeq,
            @RequestBody ReviewCreateRequestDTO reviewCreateRequestDTO) {

        reviewService.createReview(bookSeq, reviewCreateRequestDTO);
        return ResponseUtil.successResponse("리뷰가 성공적으로 등록되었습니다.").getBody();
    }


}
