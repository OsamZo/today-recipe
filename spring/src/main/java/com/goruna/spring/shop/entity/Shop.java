package com.goruna.spring.shop.entity;

import com.goruna.spring.common.aggregate.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "shop")
@NoArgsConstructor
@Getter
public class Shop extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_seq", nullable = false)
    private long shopSeq;

    @Column(name = "user_seq", nullable = false)
    private long userSeq;

    @ManyToOne
    @JoinColumn(name = "categorySeq")
    private ShopCategory shopCategory;

    @Column(name = "shop_name", nullable = false)
    private String shopName;

    @Column(name = "shop_tel", nullable = false)
    private String shopTel;

    @Column(name = "shop_ceo_name", nullable = false)
    private String shopCeoName;

    @Column(name = "shop_address", nullable = false)
    private String shopAddress;

    @Column(name = "shop_appr_status", nullable = false)
    private boolean shopApprStatus = false;

    @Column(name = "shop_open_date", nullable = false)
    private LocalDateTime shopOpenDate;

    @Column(name = "shop_introduction", nullable = false)
    private String shopIntroduction;

    @Column(name = "shop_today_comment", nullable = true)
    private String shopTodayComment;

    @Column(name = "shop_product_name", nullable = false)
    private String shopProductName;

    @Column(name = "shop_product_qty", nullable = true)
    private int shopProductQty;

    @Column(name = "shop_product_original_price", nullable = true)
    private int shopProductOriginalPrice;

    @Column(name = "shop_product_sale_price", nullable = true)
    private int shopProductSalePrice;

    @Column(name = "shop_product_desc", nullable = true)
    private String shopProductDesc;

    @Column(name = "shop_img_url", nullable = true)
    private String shopImgUrl;

    @Column(name = "shop_deadline", nullable = true)
    private LocalDateTime shopDeadline;

    @Column(name = "shop_product_img_url", nullable = true)
    private String shopProductImgUrl;

    @Column(name = "shop_del_status", nullable = false)
    private boolean shopDelStatus = false;

    @Column(name = "shop_business_img_url", nullable = false)
    private String shopBusinessImgUrl;
}
