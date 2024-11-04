package com.goruna.spring.shop.repository;

import com.goruna.spring.shop.entity.Shop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    @Query("SELECT s FROM Shop s WHERE s.shopCategory.categorySeq = :categorySeq AND s.shopApprStatus = true AND s.shopDelStatus = false")
    List<Shop> readShopsByCategory(@Param("categorySeq") Long categorySeq, Pageable pageable);
}
