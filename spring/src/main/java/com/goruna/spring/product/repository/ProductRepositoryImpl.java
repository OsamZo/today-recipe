package com.goruna.spring.product.repository;

import com.goruna.spring.product.entity.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com.goruna.spring.product.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final JPAQueryFactory jpaQueryFactory;

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