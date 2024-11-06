package com.goruna.spring.review.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.review.dto.AdminReviewShopResDTO;
import com.goruna.spring.review.dto.ReviewShopReadResDTO;
import com.goruna.spring.review.service.AdminReviewService;
import com.goruna.spring.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Review API", description = "admin 리뷰 api")
public class AdminReviewController {

    public final AdminReviewService adminReviewService;
    private final ReviewService reviewService;

    @DeleteMapping("review/{reviewSeq}")
    @Operation(summary = "관리자 리뷰 삭제 API", description = "관리자 권한으로 리뷰를 삭제합니다.")
    public ApiResponse<?> adminDeleteReview(
            @PathVariable Long reviewSeq
    ){
        adminReviewService.deleteReview(reviewSeq);

        return ResponseUtil.successResponse("관리자 권한으로 리뷰 삭제가 성공적으로 이뤄졌습니다.", null).getBody();
    }

    // 관리자 가게별 리뷰 조회
    @GetMapping("/{shopSeq}/review")
    @Operation(summary = "관리자 가게별 리뷰 조회", description = "가게 별 리뷰 조회")
    public ApiResponse<?> getAdminShopReview(
            @PathVariable Long shopSeq
    ){
        List<AdminReviewShopResDTO> getShopReviews = reviewService.getAdminReviewShops(shopSeq);
        return ResponseUtil.successResponse("리뷰를 성공적으로 조회했습니다.", getShopReviews).getBody();
    }
}
