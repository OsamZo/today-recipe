package com.goruna.spring.shop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "shop_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ShopCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categorySeq;

    @Column(nullable = false)
    private String categoryName;
}
