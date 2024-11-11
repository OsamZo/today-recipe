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
public class CreateProductReqDTO {

    private String productName;
    private String productDescription;
    private Integer productOriginalPrice;
    private Integer productSalePrice;
    private Integer productQty;
    private LocalDateTime productClosedAt;
}
