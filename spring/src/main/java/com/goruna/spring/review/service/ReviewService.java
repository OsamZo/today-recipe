package com.goruna.spring.review.service;

import com.goruna.spring.book.entity.Book;
import com.goruna.spring.book.repository.BookRepository;
import com.goruna.spring.review.dto.ReviewCreateRequestDTO;
import com.goruna.spring.review.entity.Review;
import com.goruna.spring.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        review.BookSeq(book);

        reviewRepository.save(review);
    }
}
