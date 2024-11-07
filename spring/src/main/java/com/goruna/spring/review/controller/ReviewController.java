package com.goruna.spring.review.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.review.dto.ReviewCreateRequestDTO;
import com.goruna.spring.review.dto.ReviewShopReadResDTO;
import com.goruna.spring.review.dto.ReviewUserReadResDTO;
import com.goruna.spring.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        reviewService.createReview(userSeq, bookSeq, reviewCreateRequestDTO);
        return ResponseUtil.successResponse("리뷰가 성공적으로 등록되었습니다.").getBody();
    }

    /* 내가 작성한 리뷰 조회 API */
    @GetMapping("/user/{userSeq}/review")
    @Operation(summary = "내가 작성한 리뷰 조회 API", description = "사용자가 작성한 리뷰 조회 API입니다.")
    public ApiResponse<?> getUserReviews(@PathVariable Long userSeq) {
        List<ReviewUserReadResDTO> reviews = reviewService.getUserReviews(userSeq);
        return ResponseUtil.successResponse("리뷰를 성공적으로 조회했습니다.", reviews).getBody();
    }

    @GetMapping("/shop/{shopSeq}/review")
    @Operation(summary = "매장 리뷰 조회 API", description = "특정 매장의 상품에 대한 리뷰를 조회하는 API입니다.")
    public ApiResponse<?> getShopReviews(@PathVariable Long shopSeq) {
        List<ReviewShopReadResDTO> reviews = reviewService.getReviewsByShopId(shopSeq);
        return ResponseUtil.successResponse("리뷰를 성공적으로 조회했습니다.", reviews).getBody();
    }


}
