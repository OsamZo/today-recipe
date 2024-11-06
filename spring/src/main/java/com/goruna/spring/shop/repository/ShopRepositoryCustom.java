package com.goruna.spring.shop.repository;

import com.goruna.spring.product.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShopRepositoryCustom {

    List<Product> readShopByCategorySeq(Long categorySeq, Pageable pageable);

    Long countReviewsByShopSeq(Long shopSeq);
}
