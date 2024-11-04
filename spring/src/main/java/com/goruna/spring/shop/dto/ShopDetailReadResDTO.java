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

    private String shopTodayComment;

    private String shopProductName;

    private int shopProductQty;

    private int shopProductOriginalPrice;

    private int shopProductSalePrice;

    private String shopProductDesc;

    private String shopImgUrl;

    private String shopProductImgUrl;
}

