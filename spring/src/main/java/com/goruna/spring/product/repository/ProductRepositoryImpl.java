package com.goruna.spring.product.repository;

import com.goruna.spring.product.entity.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com.goruna.spring.product.entity.QProduct.product;
import static com.goruna.spring.shop.entity.QShop.shop;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Product findCurrentProductByUserSeq(Long userSeq) {
        return jpaQueryFactory
                .selectFrom(product)
                .join(product.shop, shop) // Product와 Shop을 조인
                .where(shop.user.userSeq.eq(userSeq)) // userSeq 조건 추가
                .orderBy(product.regDate.desc()) // 등록일 기준 내림차순 정렬
                .fetchFirst(); // 가장 최근의 상품을 가져옴
    }

    @Override
    public Product readLatestProductToday(Long shopSeq, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return jpaQueryFactory
                .selectFrom(product)
                .where(product.regDate.between(startOfDay, endOfDay))
                .where(product.shop.shopSeq.eq(shopSeq))
                .orderBy(product.regDate.desc())
                .limit(1)
                .fetchOne();
    }
}

