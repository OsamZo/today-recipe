package com.goruna.spring.review.repository;

import com.goruna.spring.review.entity.QGood;
import com.goruna.spring.review.entity.QReview;
import com.goruna.spring.review.entity.Review;
import com.goruna.spring.shop.entity.QShop;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


import static com.goruna.spring.book.entity.QBook.book;
import static com.goruna.spring.review.entity.QGood.good;
import static com.goruna.spring.review.entity.QReview.review;
import static com.goruna.spring.shop.entity.QShop.shop;
import static com.goruna.spring.users.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Review> findReviewsByUserSeq(Long userSeq) {

        return jpaQueryFactory
                .selectFrom(review)
                .join(review.book, book).fetchJoin()
                .join(book.user, user).fetchJoin()
                .leftJoin(review.goods, good)
                .where(user.userSeq.eq(userSeq))
                .groupBy(review.reviewSeq)
                .fetch();
    }

    @Override
    public List<Review> findReviewsByShopId(Long shopId) {

        return jpaQueryFactory
                .selectFrom(review)
                .join(review.book.shop, shop).fetchJoin()
                .leftJoin(review.goods, good)
                .where(shop.shopSeq.eq(shopId))
                .groupBy(review.reviewSeq)
                .fetch();
    }
}
