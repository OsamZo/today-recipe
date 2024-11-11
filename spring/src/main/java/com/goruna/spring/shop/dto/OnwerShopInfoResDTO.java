package com.goruna.spring.shop.dto;

import com.goruna.spring.common.aggregate.ShopApprStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnwerShopInfoResDTO {

    private Long userSeq;
    private Long shopSeq;
    private String shopName;
    private String shopTel;
    private String shopAddress;
    private String shopIntroduction;
    private ShopApprStatus shopApprStatus;
}
