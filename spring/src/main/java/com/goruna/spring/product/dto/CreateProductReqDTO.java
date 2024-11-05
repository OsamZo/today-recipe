package com.goruna.spring.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateProductReqDTO {

    private Long shopSeq;
    private String productName;
    private String productDescription;
    private Integer productOriginalPrice;
    private Integer productSalePrice;
    private Integer productQty;
    private LocalDateTime productClosedAt;
    private String productImgUrl;
}
