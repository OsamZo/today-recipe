package com.goruna.spring.shop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@Setter
@ToString
public class ShopDetailReadResDTO {

    private String shopName;

    private LocalTime shopClosedAt;

    private String shopAddress;

    private String shopIntroduction;

    private String shopProductName;

    private String shopProductDescription;

    private int shopProductQty;

    private int productOriginalPrice;

    private int productSalePrice;

    private String shopImgUrl;

    private String shopProductImgUrl;
}

