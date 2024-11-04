package com.goruna.spring.book.service;

import com.goruna.spring.book.entity.Book;
import com.goruna.spring.book.repository.BookRepository;
import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.ShopRepository;
import com.goruna.spring.users.entity.User;
import com.goruna.spring.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

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

        Book book = new Book(shop, user, bookQty);
        book.updateProductOriginalPrice(shop.getShopProductOriginalPrice());
        book.updateProductSalePrice(shop.getShopProductSalePrice());
        bookRepository.save(book);
    }
}
