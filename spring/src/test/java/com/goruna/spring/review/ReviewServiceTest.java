package com.goruna.spring.review;

import com.goruna.spring.book.entity.Book;
import com.goruna.spring.book.repository.BookRepository;
import com.goruna.spring.review.dto.ReviewCreateRequestDTO;
import com.goruna.spring.review.entity.Review;
import com.goruna.spring.review.repository.ReviewRepository;
import com.goruna.spring.review.service.ReviewService;
import com.goruna.spring.users.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    private Long userSeq = 1L;
    private Long bookSeq = 1L;

    @Test
    void testCreateReview_Success() {
        User user = User.builder().userSeq(userSeq).build();
        Book book = Book.builder().user(user).build();
        ReviewCreateRequestDTO requestDTO = new ReviewCreateRequestDTO();
        requestDTO.setReviewContent("Test review content");

        when(bookRepository.findById(bookSeq)).thenReturn(Optional.of(book));
        when(modelMapper.map(requestDTO, Review.class)).thenReturn(new Review());

        reviewService.createReview(userSeq, bookSeq, requestDTO);

        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testCreateReview_UserNotAuthorized() {
        User otherUser = User.builder().userSeq(2L).build();
        Book unauthorizedBook = Book.builder().user(otherUser).build();
        ReviewCreateRequestDTO requestDTO = new ReviewCreateRequestDTO();
        requestDTO.setReviewContent("Unauthorized review content");

        when(bookRepository.findById(bookSeq)).thenReturn(Optional.of(unauthorizedBook));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> reviewService.createReview(userSeq, bookSeq, requestDTO));
    }

    @Test
    void testCreateReview_BookNotFound() {
        ReviewCreateRequestDTO requestDTO = new ReviewCreateRequestDTO();
        requestDTO.setReviewContent("Review for non-existent book");

        when(bookRepository.findById(bookSeq)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> reviewService.createReview(userSeq, bookSeq, requestDTO));
    }
}
