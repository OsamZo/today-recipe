package com.goruna.spring.shop.repository;

import com.goruna.spring.common.aggregate.YnType;
import com.goruna.spring.product.entity.Product;
import com.goruna.spring.shop.entity.Shop;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.goruna.spring.book.entity.QBook.book;
import static com.goruna.spring.product.entity.QProduct.product;
import static com.goruna.spring.review.entity.QReview.review;
import static com.goruna.spring.shop.entity.QShop.shop;
import static com.goruna.spring.shop.entity.QShopCategory.shopCategory;

@Repository
@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // 오늘의 특가 리스트 조회
    @Override
    public List<Product> readLatest5ShopsToday(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        // 각 shopSeq별로 오늘 등록된 것 중 가장 최신 productSeq를 가져오는 서브쿼리
        SubQueryExpression<Long> latestProductSubquery = JPAExpressions
                .select(product.productSeq.max())
                .from(product)
                .where(product.regDate.between(startOfDay, endOfDay))
                .groupBy(product.shop.shopSeq)
                .orderBy(product.regDate.desc())
                .limit(1);

        // 오늘 올라온 상품 중 가장 마지막에 올라온 상품을 shop과 shopCategory와 조인
        return jpaQueryFactory
                .selectFrom(product)
                .join(product.shop, shop).fetchJoin()
                .join(shop.shopCategory, shopCategory).fetchJoin()
                .where(product.productSeq.in(latestProductSubquery))
                .where(shop.shopApprStatus.eq(YnType.Y))
                .where(shop.shopDelStatus.eq(YnType.N))
                .orderBy(product.regDate.desc())
                .limit(5)
                .fetch();
    }

    // 카테고리별 매장 리스트 조회
    @Override
    public List<Product> readShopByCategorySeq(Long categorySeq, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(product)
                .join(product.shop, shop).fetchJoin()
                .join(shop.shopCategory, shopCategory).fetchJoin()
                .where(shopCategory.categorySeq.eq(categorySeq))
                .where(shop.shopApprStatus.eq(YnType.Y))
                .where(shop.shopDelStatus.eq(YnType.N))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    // 매장별 리뷰 개수 카운트
    @Override
    public Long countReviewsByShopSeq(Long shopSeq) {
        return jpaQueryFactory
                .selectFrom(review)
                .join(review.book, book).fetchJoin()
                .join(book.product, product).fetchJoin()
                .join(product.shop, shop).fetchJoin()
                .where(shop.shopSeq.eq(shopSeq))
                .fetchCount();
    }
      
    @Override
    public Shop getUserShopStatus(Long currentUserSeq) {
        return jpaQueryFactory
                .selectFrom(shop)
                .where(shop.user.userSeq.eq(currentUserSeq))
                .fetchOne();
    }
}
