package com.goruna.spring.shop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShopListReadResDTO {

    private Long shopSeq;

    private String shopName;

    private String shopImgUrl;

    private String categoryName;

    private int productOriginalPrice;

    private int productSalePrice;
}
