package com.goruna.spring.review.repository;


import com.goruna.spring.review.entity.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


import static com.goruna.spring.book.entity.QBook.book;
import static com.goruna.spring.product.entity.QProduct.product;
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
    public List<Review> findReviewsByShopId(Long shopSeq) {
        if (shopSeq == null) {
            throw new IllegalArgumentException("ShopSeq cannot be null");
        }

        // FetchJoin 전 객체 초기화 및 null 체크
        return jpaQueryFactory
                .selectFrom(review)
                .join(review.book, book).fetchJoin() // book이 null이 아닌지 확인 필요
                .join(book.product, product).fetchJoin() // product가 null이 아닌지 확인 필요
                .join(product.shop, shop).fetchJoin() // shop이 null이 아닌지 확인 필요
                .leftJoin(review.goods, good)
                .where(shop.shopSeq.eq(shopSeq))
                .groupBy(review.reviewSeq)
                .fetch();
    }
}
