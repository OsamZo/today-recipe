package com.goruna.spring.shop.dto;

import com.goruna.spring.common.aggregate.ShopApprStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserShopStatusResponse {
    private ShopApprStatus shopApprStatus;
}
