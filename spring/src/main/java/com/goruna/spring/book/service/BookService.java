package com.goruna.spring.book.service;

import com.goruna.spring.book.dto.BookListReadResDTO;
import com.goruna.spring.book.entity.Book;
import com.goruna.spring.book.repository.BookRepository;
import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.ShopRepository;
import com.goruna.spring.users.entity.User;
import com.goruna.spring.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void createBook(Long shopSeq, Long userSeq, int bookQty) {
        User user = userRepository.findById(userSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND));

        Shop shop = shopRepository.findById(shopSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.SHOP_NOT_FOUND));

        List<Book> books = bookRepository.findByShopShopSeq(shopSeq);

        int totalBookedQty = 0;

        for (Book book : books) {
            totalBookedQty += book.getBookQty();
        }

        if (bookQty >totalBookedQty) {
            throw new CustomException(ErrorCodeType.INVALID_VALUE);
        }

        Book book = Book.builder()
                .shop(shop)
                .user(user)
                .bookQty(bookQty)
                .productOriginalPrice(shop.getShopProductOriginalPrice())
                .productSalePrice(shop.getShopProductSalePrice())
                .build();

        bookRepository.save(book);
    }

    // 회원별 예약 리스트 가져오기
    public List<BookListReadResDTO> readBookListByUserSeq(Long userSeq) {
        List<Book> books = bookRepository.findByUserUserSeq(userSeq);
        return books.stream()
                .map(book -> {
                    BookListReadResDTO bookListReadResDTO = modelMapper.map(book, BookListReadResDTO.class);
                    bookListReadResDTO.setShopName(book.getShop().getShopName());
                    bookListReadResDTO.setShopProductName(book.getShop().getShopProductName());
                    bookListReadResDTO.setShopClosedAt(book.getShop().getShopClosedAt());
                    bookListReadResDTO.setShopAddress(book.getShop().getShopAddress());
                    bookListReadResDTO.setShopImgUrl(book.getShop().getShopImgUrl());
                    bookListReadResDTO.setIsBookCancelled(book.getIsBookCancelled());
                    return bookListReadResDTO;
                })
                .collect(Collectors.toList());
    }
}
