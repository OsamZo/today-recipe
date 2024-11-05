package com.goruna.spring.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductReqDTO {

    private String shopTodayComment;
    private String shopProductName;
    private String shopProductDesc;
    private Integer shopProductOriginalPrice;
    private Integer shopProductSalePrice;
    private Integer shopProductQty;
    private String shopImgUrl;
}
