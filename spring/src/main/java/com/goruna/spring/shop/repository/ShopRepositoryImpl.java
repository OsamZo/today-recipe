package com.goruna.spring.shop.repository;

import com.goruna.spring.common.aggregate.ShopApprStatus;
import com.goruna.spring.common.aggregate.YnType;
import com.goruna.spring.product.entity.Product;
import com.goruna.spring.shop.entity.Shop;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.goruna.spring.book.entity.QBook.book;
import static com.goruna.spring.product.entity.QProduct.product;
import static com.goruna.spring.review.entity.QReview.review;
import static com.goruna.spring.shop.entity.QShop.shop;
import static com.goruna.spring.shop.entity.QShopCategory.shopCategory;

@Repository
@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    // 오눌 등록된 상품의 productSeq를 가져오는 서브쿼리
    private SubQueryExpression<Long> getLatestProductSubquery(LocalDateTime startOfDay, LocalDateTime endOfDay, Long categorySeq) {
        // 카테고리 조건을 선택적으로 추가
        BooleanExpression categoryCondition = (categorySeq != null)
                ? product.shop.shopCategory.categorySeq.eq(categorySeq)
                : null;

        // 날짜 조건(오늘 등록된 상품만)
        BooleanExpression dateCondition = product.regDate.between(startOfDay, endOfDay);

        // 두 조건을 결합
        BooleanExpression combinedCondition = (categoryCondition != null)
                ? categoryCondition.and(dateCondition)
                : dateCondition; // 카테고리 조건이 없으면 날짜 조건만 사용

        return JPAExpressions
                .select(product.productSeq.max()) // 가장 최신 productSeq를 가져옴
                .from(product)
                .where(combinedCondition) // 결합된 조건을 사용
                .groupBy(product.shop.shopSeq)
                .orderBy(product.regDate.desc())
                .limit(1);
    }

    // 공통 쿼리 로직 - 상품과 매장 조인 및 필터링
    private List<Product> getProductsWithShopJoin(SubQueryExpression<Long> latestProductSubquery, Pageable pageable) {
        long offset = (pageable != null && !pageable.isUnpaged()) ? pageable.getOffset() : 0;
        int pageSize = (pageable != null && !pageable.isUnpaged()) ? pageable.getPageSize() : Integer.MAX_VALUE;
        return jpaQueryFactory
                .selectFrom(product)
                .join(product.shop, shop).fetchJoin()
                .join(shop.shopCategory, shopCategory).fetchJoin()
                .where(product.productSeq.in(latestProductSubquery)) // 최신 상품만
                .where(shop.shopApprStatus.eq(ShopApprStatus.APPROVE)) // 승인된 매장만
                .where(shop.shopDelStatus.eq(YnType.N)) // 삭제되지 않은 매장만
                .offset(offset)
                .limit(pageSize)
                .fetch();
    }

    // 오늘의 특가 리스트 조회
    @Override
    public List<Product> readLatest5ShopsToday(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        SubQueryExpression<Long> latestProductSubquery = getLatestProductSubquery(startOfDay, endOfDay, null);

        return getProductsWithShopJoin(latestProductSubquery, Pageable.unpaged()) // 전체 조회는 Pageable.unpaged() 사용
                .stream()
                .limit(5)
                .collect(Collectors.toList());
    }

    // 전체 매장 리스트 조회
    @Override
    public List<Product> readShopsAllToday(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        SubQueryExpression<Long> latestProductSubquery = getLatestProductSubquery(startOfDay, endOfDay, null);

        return getProductsWithShopJoin(latestProductSubquery, null); // 전체 조회 시 페이지네이션이 필요 없으면 null
    }

    // 카테고리별 매장 리스트 조회
    @Override
    public List<Product> readShopByCategorySeq(Long categorySeq, Pageable pageable, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        SubQueryExpression<Long> latestProductSubquery = getLatestProductSubquery(startOfDay, endOfDay, categorySeq);

        return getProductsWithShopJoin(latestProductSubquery, pageable);
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

    @Override
    public Shop findShopByUserSeq(Long userSeq) {
        return jpaQueryFactory
                .selectFrom(shop)
                .where(shop.user.userSeq.eq(userSeq))
                .fetchOne();
    }
}
