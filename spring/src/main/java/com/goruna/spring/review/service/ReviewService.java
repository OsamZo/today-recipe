package com.goruna.spring.review.service;

import com.goruna.spring.book.entity.Book;
import com.goruna.spring.book.repository.BookRepository;
import com.goruna.spring.review.dto.ReviewCreateRequestDTO;
import com.goruna.spring.review.dto.ReviewShopReadResDTO;
import com.goruna.spring.review.dto.ReviewUserReadResDTO;
import com.goruna.spring.review.entity.Review;
import com.goruna.spring.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void createReview(Long bookSeq, ReviewCreateRequestDTO reviewCreateRequestDTO) {
        Book book = bookRepository.findById(bookSeq)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Review review = modelMapper.map(reviewCreateRequestDTO, Review.class);
        review.Book(book);

        reviewRepository.save(review);
    }

    /* 회원 별 리뷰 조회 */
    @Transactional
    public List<ReviewUserReadResDTO> getUserReviews(Long userSeq) {
        List<Review> reviews = reviewRepository.findReviewsByUserSeq(userSeq);

        return reviews.stream()
                .map(review -> new ReviewUserReadResDTO(
                        review.getBook().getProduct().getShop().getShopName(),
                        review.getBook().getUser().getUserNickname(),
                        review.getReviewContent(),
                        review.getRegDate(),
                        review.getGoods().size()
                ))
                .collect(Collectors.toList());
    }

    /* 매장 별 리뷰 조회 */
    public List<ReviewShopReadResDTO> getReviewsByShopId(Long shopId) {
        List<Review> reviews = reviewRepository.findReviewsByShopId(shopId);

        return reviews.stream()
                .map(review -> new ReviewShopReadResDTO(
                        review.getBook().getProduct().getShop().getShopImgUrl(),
                        review.getBook().getProduct().getShop().getShopName(),
                        review.getBook().getUser().getUserNickname(),
                        review.getRegDate(),
                        review.getReviewContent(),
                        review.getGoods().size()
                ))
                .collect(Collectors.toList());
    }
}
