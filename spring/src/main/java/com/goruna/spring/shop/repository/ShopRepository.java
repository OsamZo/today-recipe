package com.goruna.spring.shop.repository;

import com.goruna.spring.shop.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long>, ShopRepositoryCustom {

    Page<Shop> findByShopNameContaining(Pageable pageable, String shopName);
}