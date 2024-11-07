package com.goruna.spring.product.repository;

import com.goruna.spring.product.entity.Product;
import java.time.LocalDateTime;


public interface ProductRepositoryCustom {
  
    Product findCurrentProductByUserSeq(Long userSeq);
  
    Product readLatestProductToday(Long shopSeq, LocalDateTime startOfDay, LocalDateTime endOfDay);
}

