package com.goruna.spring.product.repository;

import com.goruna.spring.product.entity.Product;

public interface ProductRepositoryCustom {
    Product findCurrentProductByUserSeq(Long userSeq);
}
