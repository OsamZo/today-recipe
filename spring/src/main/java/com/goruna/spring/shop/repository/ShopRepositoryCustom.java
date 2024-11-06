package com.goruna.spring.shop.repository;

import com.goruna.spring.shop.entity.Shop;

public interface ShopRepositoryCustom {

    Shop getUserShopStatus(Long currentUserSeq);
}
