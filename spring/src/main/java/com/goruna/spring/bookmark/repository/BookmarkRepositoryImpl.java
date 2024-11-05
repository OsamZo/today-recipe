package com.goruna.spring.bookmark.repository;

import com.goruna.spring.bookmark.entity.Bookmark;
import com.goruna.spring.bookmark.entity.QBookmark;
import com.goruna.spring.product.entity.QProduct;
import com.goruna.spring.shop.entity.QShop;
import com.goruna.spring.shop.entity.QShopCategory;
import com.goruna.spring.users.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goruna.spring.bookmark.entity.QBookmark.bookmark;
import static com.goruna.spring.product.entity.QProduct.product;
import static com.goruna.spring.shop.entity.QShop.shop;
import static com.goruna.spring.shop.entity.QShopCategory.shopCategory;


@Repository
@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Bookmark> findBookmarksByUserSeq(Long userSeq) {

        // Bookmark, Shop, ShopCategory 정보만 조회
        return jpaQueryFactory
                .selectFrom(bookmark)
                .join(bookmark.shop, shop).fetchJoin()
                .join(shop.shopCategory, shopCategory).fetchJoin()
                .where(bookmark.user.userSeq.eq(userSeq))
                .fetch();
    }
}
