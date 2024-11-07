package com.goruna.spring.product.repository;

import com.goruna.spring.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    Product findFirstByShop_ShopSeqOrderByRegDateDesc(Long shopSeq);
    Product findByShopShopSeq(Long shopSeq);
}
