package com.goruna.spring.shop.repository;

import com.goruna.spring.shop.entity.Shop;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.goruna.spring.shop.entity.QShop.shop;

@Repository
@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public Shop getUserShopStatus(Long currentUserSeq) {
        return queryFactory
                .selectFrom(shop)
                .where(shop.user.userSeq.eq(currentUserSeq))
                .fetchOne();
    }
}
