package com.goruna.spring.review.repository;

import com.goruna.spring.review.entity.Review;

import java.util.List;

public interface ReviewRepositoryCustom {

    List<Review> findReviewsByUserSeq(Long userSeq);

    List<Review> findReviewsByShopId(Long shopId);
}
