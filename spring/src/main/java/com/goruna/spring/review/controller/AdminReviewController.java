package com.goruna.spring.review.controller;

import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.review.service.AdminReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Review API", description = "admin 리뷰 api")
public class AdminReviewController {

    public final AdminReviewService adminReviewService;

    @DeleteMapping("review/{reviewSeq}")
    @Operation(summary = "관리자 리뷰 삭제 API", description = "관리자 권한으로 리뷰를 삭제합니다.")
    public ApiResponse<?> adminDeleteReview(
            @PathVariable Long reviewSeq
    ){
        adminReviewService.deleteReview(reviewSeq);

        return ResponseUtil.successResponse("관리자 권한으로 리뷰 삭제가 성공적으로 이뤄졌습니다.", null).getBody();
    }
}
