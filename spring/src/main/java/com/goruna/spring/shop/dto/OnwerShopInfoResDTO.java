package com.goruna.spring.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnwerShopInfoResDTO {

    private Long userSeq;
    private Long shopSeq;
    private String shopName;
    private String shopAddress;
    private String shopIntroduction;
}
