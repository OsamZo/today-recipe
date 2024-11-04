package com.goruna.spring.shop.repository;

import com.goruna.spring.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminShopRepository extends JpaRepository<Shop, Long> {

}
