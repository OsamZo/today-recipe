package com.goruna.spring.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateProductReqDTO {

    private Long shopSeq;
    private String todayComment;
    private String productName;
    private Integer productOriginalPrice;
    private Integer productSalePrice;
    private Integer productQty;
    private LocalDateTime productClosedAt;
    private String productImgUrl;
}
