package com.goruna.spring.shop.repository;

import com.goruna.spring.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerShopRepository extends JpaRepository<Shop, Long> {

}
