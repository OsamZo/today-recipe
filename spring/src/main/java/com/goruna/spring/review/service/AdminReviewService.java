package com.goruna.spring.review.service;

import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.review.entity.Review;
import com.goruna.spring.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminReviewService {

    private final ReviewRepository reviewRepository;

    public void deleteReview(Long reviewSeq) {


//        Review review = reviewRepository.findById(reviewSeq)
//                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));


        reviewRepository.deleteById(reviewSeq);

    }
}
