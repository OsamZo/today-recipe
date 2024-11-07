package com.goruna.spring.shop.dto;

import com.goruna.spring.common.aggregate.ShopApprStatus;
import com.goruna.spring.common.aggregate.YnType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminShopResponseDTO {

    private Long shopSeq;
    private String shopName;
    private String categorySeq;
    private String categoryName;
    private String shopAddress;
    private String shopImgUrl;
    private ShopApprStatus shopApprStatus;
    private YnType shopDelStatus;
}
