package com.goruna.spring.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminShopSearchResponseDTO {

    private Long shopSeq;
    private String shopName;
    private Long CategorySeq;
    private String CategoryName;
    private String shopAddress;
}
