package com.goruna.spring.review.service;

import com.goruna.spring.review.dto.GoodRequestDTO;
import com.goruna.spring.review.dto.GoodResDTO;
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

    /* 좋아요 조회 */
    public GoodResDTO readGood(Long userSeq, Long reviewSeq){

        Review review = reviewRepository.findById(reviewSeq)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        User user = userRepository.findById(userSeq)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Good good = goodRepository.findByUserAndReview(user, review)
                .orElse(null);

        if (good != null) {
            // `Good` 객체가 존재하면 해당 객체의 정보로 DTO 생성
            return new GoodResDTO(good.getGoodSeq());
        } else {
            // `Good` 객체가 없을 경우 기본 값으로 반환
            return new GoodResDTO(null);
        }
    }

    /* 좋아요 추가 */
    @Transactional
    public GoodResDTO createGood(GoodRequestDTO requestDTO) {

        Review review = reviewRepository.findById(requestDTO.getReviewSeq())
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        User user = userRepository.findById(requestDTO.getUserSeq())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Good good = Good.builder()
                .user(user)
                .review(review)
                .build();
        goodRepository.save(good);

        return modelMapper.map(good, GoodResDTO.class);
    }

    /* 좋아요 삭제 */
    @Transactional
    public boolean deleteGood(Long goodSeq, Long userSeq) {
        Good good = goodRepository.findById(goodSeq)
                .orElseThrow(() -> new IllegalArgumentException("Good not found"));

        // 원시 타입의 long을 객체 타입으로 변환하여 비교
        if (!Long.valueOf(good.getUser().getUserSeq()).equals(userSeq)) {
            throw new SecurityException("해당 좋아요를 삭제할 권한이 없습니다.");
        }

        goodRepository.delete(good);
        return true;
    }
}
