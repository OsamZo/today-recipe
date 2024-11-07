package com.goruna.spring.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentProductResDTO {

    private Long shopSeq;
    private Long productSeq;
    private String productName;
    private String productDesc;
    private Integer productOriginalPrice;
    private Integer productSalePrice;
    private Integer productQty;
    private LocalDateTime productClosedAt;
    private String productImgUrl;
}
