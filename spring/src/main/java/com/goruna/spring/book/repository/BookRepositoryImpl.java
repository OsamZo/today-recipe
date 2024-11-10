package com.goruna.spring.book.repository;

import com.goruna.spring.book.dto.OwnerShopBooksResDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.goruna.spring.book.entity.QBook.book;
import static com.goruna.spring.product.entity.QProduct.product;
import static com.goruna.spring.shop.entity.QShop.shop;
import static com.goruna.spring.users.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OwnerShopBooksResDTO> findOwnerShopBooksByUserSeq(Long userSeq) {

        return jpaQueryFactory
                .select(Projections.constructor(
                        OwnerShopBooksResDTO.class,
                        user.userNickname, // 예약자 닉네임
                        product.productName, // 제품 이름
                        book.bookQty, // 예약 수량
                        book.bookQty.multiply(product.productSalePrice).as("totalPrice"), // 총 가격
                        book.bookIsProductReceived // 제품 수령 여부
                ))
                .from(book)
                .join(product).on(book.product.productSeq.eq(product.productSeq))
                .join(shop).on(product.shop.shopSeq.eq(shop.shopSeq))
                .join(user).on(book.user.userSeq.eq(user.userSeq))
                .where(shop.user.userSeq.eq(userSeq))
                .fetch();
    }

    // 오늘 해당 매장에 예약한 상품이 있는지 카운트
    @Override
    public Long CountTodayBookByUserSeqAndShopSeq(Long userSeq, Long shopSeq, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return jpaQueryFactory
                .selectFrom(book)
                .join(book.product, product).fetchJoin()
                .join(product.shop, shop).fetchJoin()
                .where(book.user.userSeq.eq(userSeq)
                        .and(shop.shopSeq.eq(shopSeq))
                        .and(book.regDate.between(startOfDay, endOfDay)))
                .fetchCount();
    }
}
