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
//        Long shopSeq = ownerShopRepository.findById(userSeq)
//                .orElseThrow(() -> new CustomException(ErrorCodeType.SHOP_NOT_FOUND)).getShopSeq();

        QUser user = QUser.user;
        QShop shop = QShop.shop;
        QProduct product = QProduct.product;
        QBook book = QBook.book;

        return jpaQueryFactory
                .select(Projections.constructor(OwnerShopBooksResDTO.class,
                        user.userNickname,
                        book.product.productName,
                        book.bookQty,
                        book.product.productSalePrice.multiply(book.bookQty).as("TotalPrice"),
                        book.bookIsProductReceived
                        ))
                .from(user)
//                .join(shop).on(user.userSeq.eq(shop.user.userSeq))
//                .join(product).on(shop.shopSeq.eq(product.shop.shopSeq))
//                .join(book).on(product.productSeq.eq(book.bookSeq))
//                .where(shop.user.userSeq.eq(userSeq))
                .join(user).on(shop.user.userSeq.eq(user.userSeq)) // 사용자와 매장 조인
                .join(product).on(shop.shopSeq.eq(product.shop.shopSeq)) // 매장과 제품 조인
                .join(book).on(product.productSeq.eq(book.product.productSeq)) // 제품과 예약 조인
                .where(shop.user.userSeq.eq(userSeq)) // 특정 유저의 매장
                .fetch();
    }
}


