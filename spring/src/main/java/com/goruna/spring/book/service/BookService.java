package com.goruna.spring.book.service;

import com.goruna.spring.book.dto.BookListReadResDTO;
import com.goruna.spring.book.entity.Book;
import com.goruna.spring.book.repository.BookRepository;
import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.product.entity.Product;
import com.goruna.spring.product.repository.ProductRepository;
import com.goruna.spring.users.entity.User;
import com.goruna.spring.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    // 예약 생성하기
    @Transactional
    public void createBook(Long productSeq, Long userSeq, int bookQty) {
        User user = userRepository.findById(userSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND));
        Product product = productRepository.findById(productSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.PRODUCT_NOT_FOUND));

        // 해당 상품의 남은 수량
        int productQty = product.getProductQty();

        // 예약하려는 수량이 남은 수량보다 많다면 에러 발생, 그렇지 않다면 재고 변경
        if (bookQty <= productQty) {
            product.updateProductQty(productQty - bookQty);
            productRepository.save(product);
        } else {
            throw new CustomException(ErrorCodeType.INVALID_VALUE);
        }

        // 예약된 날짜와 같은 날짜에 같은 매장에서 해당 사용자가 예약한 적이 있는지 체크(취소 제외)
        // 서울 날짜
        LocalDateTime startOfDay = LocalDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        Long shopSeq = product.getShop().getShopSeq();
        Long countBookingToday = bookRepository.CountTodayBookByUserSeqAndShopSeq(userSeq, shopSeq, startOfDay, endOfDay);

        // 이미 예약한 내역이 있으면 예외 처리
        if (countBookingToday > 0) {
            throw new CustomException(ErrorCodeType.DUPLICATE_BOOKING);
        }

        Book book = Book.builder()
                .user(user)
                .product(product)
                .bookQty(bookQty)
                .build();

        bookRepository.save(book);
    }

    // 회원별 예약 리스트 가져오기
    public List<BookListReadResDTO> readBookListByUserSeq(Long userSeq) {
        List<Book> books = bookRepository.findByUserUserSeq(userSeq);
        return books.stream()
                .map(book -> {
                    BookListReadResDTO bookListReadResDTO = modelMapper.map(book, BookListReadResDTO.class);
                    bookListReadResDTO.setShopName(book.getProduct().getShop().getShopName());
                    bookListReadResDTO.setShopClosedAt(book.getProduct().getProductClosedAt());
                    bookListReadResDTO.setShopAddress(book.getProduct().getShop().getShopAddress());
                    bookListReadResDTO.setShopImgUrl(book.getProduct().getShop().getShopImgUrl());
                    bookListReadResDTO.setIsBookCancelled(book.getIsBookCancelled());
                    return bookListReadResDTO;
                })
                .collect(Collectors.toList());
    }

    // 예약 취소하기
    @Transactional
    public void cancelBook(Long bookSeq) {
        Book book = bookRepository.findById(bookSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));
        Product product = productRepository.findById(book.getProduct().getProductSeq())
                        .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        book.cancelBook();
        product.updateProductQty(product.getProductQty() + book.getBookQty());

        bookRepository.save(book);
    }
}