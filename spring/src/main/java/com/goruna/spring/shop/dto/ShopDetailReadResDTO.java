package com.goruna.spring.shop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ShopDetailReadResDTO {

    private Long shopSeq;

    private String shopName;

    private int shopReviewCount;

    private LocalDateTime productClosedAt;

    private String shopAddress;

    private String shopTel;

    private String shopIntroduction;

    private String categoryName;

    private Long productSeq;

    private String productName;

    private String productDescription;

    private int productQty;

    private int productOriginalPrice;

    private int productSalePrice;

    private String shopImgUrl;

    private String productImgUrl;
}

