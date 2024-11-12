package com.goruna.spring.product.entity;

import com.goruna.spring.common.aggregate.entity.BaseTimeEntity;
import com.goruna.spring.shop.entity.Shop;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    public void updateProductQty(int productQty) {
        this.productQty = productQty;
    }

    public void addShop(Shop shop) {
        this.shop = shop;
    }

    public void addProductImg(String productImg) {
        this.productImgUrl = productImg;
    }

    @Builder
    public Product(Long productSeq, Shop shop, String productName, String productDescription, Integer productQty,
                Integer productOriginalPrice, Integer productSalePrice, LocalDateTime productClosedAt, String productImgUrl) {
            this.productSeq = productSeq;
            this.shop = shop;
            this.productName = productName;
            this.productDescription = productDescription;
            this.productQty = productQty;
            this.productOriginalPrice = productOriginalPrice;
            this.productSalePrice = productSalePrice;
            this.productClosedAt = productClosedAt;
            this.productImgUrl = productImgUrl;
        }
    }