package com.goruna.spring.product.entity;

import com.goruna.spring.common.aggregate.entity.BaseTimeEntity;
import com.goruna.spring.shop.entity.Shop;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_seq", nullable = false)
    private Long productSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_seq", nullable = false)
    private Shop shop;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_description", nullable = true)
    private String productDescription;

    @Column(name = "product_qty", nullable = false)
    private Integer productQty = 0;

    @Column(name = "product_original_price", nullable = false)
    private Integer productOriginalPrice;

    @Column(name = "product_sale_price", nullable = false)
    private Integer productSalePrice;

    @Column(name = "product_closed_at", nullable = false)
    private LocalDateTime productClosedAt;

    @Column(name = "product_img_url", nullable = true)
    private String productImgUrl;

}
