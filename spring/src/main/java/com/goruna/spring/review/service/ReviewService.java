package com.goruna.spring.review.service;

import com.goruna.spring.book.entity.Book;
import com.goruna.spring.book.repository.BookRepository;
import com.goruna.spring.review.dto.AdminReviewShopResDTO;
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

    /* 리뷰 추가 API */
    @Transactional
    public void createReview(Long userSeq, Long bookSeq, ReviewCreateRequestDTO reviewCreateRequestDTO) {
        Book book = bookRepository.findById(bookSeq)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (book.getUser().getUserSeq() != userSeq) {
            throw new IllegalArgumentException("리뷰를 작성할 수 있는 권한이 없습니다.");
        }

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
                        review.getBook().getUser().getUserNickname(),
                        review.getReviewContent(),
                        review.getRegDate(),
                        review.getGoods().size()
                ))
                .collect(Collectors.toList());
    }

    /* 매장 별 리뷰 조회 */
    public List<ReviewShopReadResDTO> getReviewsByShopId(Long shopSeq) {
        List<Review> reviews = reviewRepository.findReviewsByShopId(shopSeq);

        return reviews.stream()
                .map(review -> new ReviewShopReadResDTO(
                        review.getBook().getUser().getUserNickname(),
                        review.getRegDate(),
                        review.getReviewContent(),
                        review.getGoods().size()
                ))
                .collect(Collectors.toList());
    }

    /*관리자 매장 별 리뷰 조회*/
    public List<AdminReviewShopResDTO> getAdminReviewShops(Long shopSeq) {

        List<Review> reviews = reviewRepository.findByShopSeq(shopSeq);

        return reviews.stream()
                .map(review -> new AdminReviewShopResDTO(
                        review.getBook().getProduct().getShop().getShopSeq(),
                        review.getBook().getProduct().getShop().getShopName(),
                        review.getBook().getProduct().getShop().getShopImgUrl(),
                        review.getBook().getUser().getUserSeq(),
                        review.getBook().getUser().getUserNickname(),
                        review.getReviewSeq(),
                        review.getReviewContent(),
                        review.getReviewStatus()
                ))
                .collect(Collectors.toList());
    }
}
