package com.goruna.spring.shop.repository;

import com.goruna.spring.product.entity.Product;
import com.goruna.spring.shop.entity.Shop;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ShopRepositoryCustom {

    List<Product> readLatest5ShopsToday(LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Product> readShopByCategorySeq(Long categorySeq, Pageable pageable);

    Long countReviewsByShopSeq(Long shopSeq);
  
    Shop getUserShopStatus(Long currentUserSeq);

    Shop findShopByUserSeq(Long userSeq);
}
