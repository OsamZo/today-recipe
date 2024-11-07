package com.goruna.spring.review.service;

import com.goruna.spring.review.dto.GoodDeleteResDTO;
import com.goruna.spring.review.dto.GoodRequestDTO;
import com.goruna.spring.review.dto.GoodCreateResDTO;
import com.goruna.spring.review.entity.Good;
import com.goruna.spring.review.entity.Review;
import com.goruna.spring.review.repository.GoodRepository;
import com.goruna.spring.review.repository.ReviewRepository;
import com.goruna.spring.users.entity.User;
import com.goruna.spring.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GoodService {

    private final GoodRepository goodRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /* 좋아요 추가 */
    @Transactional
    public GoodCreateResDTO createGood(GoodRequestDTO requestDTO) {

        Review review = reviewRepository.findById(requestDTO.getReviewSeq())
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        User user = userRepository.findById(requestDTO.getUserSeq())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Good good = Good.builder()
                .user(user)
                .review(review)
                .build();
        goodRepository.save(good);

        return modelMapper.map(good, GoodCreateResDTO.class);
    }

    /* 좋아요 삭제 */
    @Transactional
    public GoodDeleteResDTO deleteGood(Long goodSeq) {
        Good good = goodRepository.findById(goodSeq)
                .orElseThrow(() -> new IllegalArgumentException("Good not found"));

        goodRepository.delete(good);

        return new GoodDeleteResDTO(good.getIsClicked());
    }
}
