package com.goruna.spring.product.repository;

import com.goruna.spring.book.entity.Book;
import com.goruna.spring.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByShopShopSeq(Long shopSeq);
}
