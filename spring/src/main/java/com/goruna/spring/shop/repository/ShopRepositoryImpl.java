package com.goruna.spring.shop.repository;

import com.goruna.spring.product.entity.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goruna.spring.product.entity.QProduct.product;
import static com.goruna.spring.shop.entity.QShop.shop;
import static com.goruna.spring.shop.entity.QShopCategory.shopCategory;

@Repository
@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Product> readShopByCategorySeq(Long categorySeq, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(product)
                .join(product.shop, shop).fetchJoin()
                .join(shop.shopCategory, shopCategory).fetchJoin()
                .where(shopCategory.categorySeq.eq(categorySeq))
                .fetch();
    }
}
