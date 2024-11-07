package com.goruna.spring.shop.repository;

import com.goruna.spring.common.aggregate.ShopApprStatus;
import com.goruna.spring.common.aggregate.YnType;
import com.goruna.spring.product.entity.Product;
import com.goruna.spring.shop.entity.Shop;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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

    // 카테고리별 매장 리스트 조회
    @Override
    public List<Product> readShopByCategorySeq(Long categorySeq, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(product)
                .join(product.shop, shop).fetchJoin()
                .join(shop.shopCategory, shopCategory).fetchJoin()
                .where(shopCategory.categorySeq.eq(categorySeq))
                .where(shop.shopApprStatus.eq(ShopApprStatus.APPROVE))
                .where(shop.shopDelStatus.eq(YnType.N))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

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
