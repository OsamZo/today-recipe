package com.goruna.spring.book.service;

import com.goruna.spring.book.dto.OwnerShopBooksResDTO;
import com.goruna.spring.book.entity.QBook;
import com.goruna.spring.book.repository.BookRepository;
import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.product.entity.QProduct;
import com.goruna.spring.shop.entity.QShop;
import com.goruna.spring.shop.repository.OwnerShopRepository;
import com.goruna.spring.users.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Integer.sum;

@Service
@RequiredArgsConstructor
public class OwnerBookService {

    private final BookRepository bookRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final OwnerShopRepository ownerShopRepository;

    public List<OwnerShopBooksResDTO> getOwnerShopBooks() {

        Long userSeq = 1L;
        Long shopSeq = ownerShopRepository.findById(userSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.SHOP_NOT_FOUND)).getShopSeq();

        QUser user = QUser.user;
        QShop shop = QShop.shop;
        QProduct product = QProduct.product;
        QBook book = QBook.book;

        return jpaQueryFactory
                .select(Projections.constructor(OwnerShopBooksResDTO.class,
                        user.userNickname,
                        product.productName,
                        book.bookQty,
                        Expressions.numberTemplate(Integer.class, "{0} * {1}", product.productSalePrice, book.bookQty),    // 두 컬럼을 곱한 값
                        book.bookIsProductReceived
                        ))
                .from(user)
                .join(shop).on(user.userSeq.eq(shop.user.userSeq))
                .join(product).on(shop.shopSeq.eq(product.shop.shopSeq))
                .join(book).on(product.productSeq.eq(book.bookSeq))
                .where(shop.shopSeq.eq(shopSeq))
                .fetch();
    }
}


