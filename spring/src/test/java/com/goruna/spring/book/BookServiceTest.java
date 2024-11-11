package com.goruna.spring.book;

import com.goruna.spring.book.service.BookService;
import com.goruna.spring.common.aggregate.UserRole;
import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.product.entity.Product;
import com.goruna.spring.product.repository.ProductRepository;
import com.goruna.spring.users.entity.User;
import com.goruna.spring.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    private Long userSeq;
    private Long productSeq;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userSeq = 1L;
        productSeq = 1L;

        // 공통적으로 사용될 User와 Product 객체 생성 및 stubbing
        User user = User.builder()
                .userSeq(userSeq)
                .userEmail("user@example.com")
                .userNickname("nickname")
                .userRole(UserRole.USER)
                .build();

        Product product = Product.builder()
                .productSeq(productSeq)
                .productName("Product Name")
                .productQty(5)
                .productOriginalPrice(1000)
                .productSalePrice(800)
                .productClosedAt(LocalDateTime.now().plusDays(1))
                .build();

        // 모든 테스트에서 기본적으로 유효한 User와 Product를 반환하도록 설정
        lenient().when(userRepository.findById(userSeq)).thenReturn(Optional.of(user));
        lenient().when(productRepository.findById(productSeq)).thenReturn(Optional.of(product));
    }
    @Test
    void testCreateBook_ThrowsInvalidValueException() {
        // Given
        int bookQty = 10;

        // When & Then
        assertThrows(CustomException.class, () -> bookService.createBook(productSeq, userSeq, bookQty));
    }

}
