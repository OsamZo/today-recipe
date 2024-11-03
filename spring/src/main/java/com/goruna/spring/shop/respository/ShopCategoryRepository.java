package com.goruna.spring.shop.respository;

import com.goruna.spring.shop.entity.ShopCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopCategoryRepository extends JpaRepository<ShopCategory, Long> {
}
