package com.goruna.spring.shop.service;

import com.goruna.spring.common.util.CustomUserUtils;
import com.goruna.spring.shop.dto.UserShopStatusResponse;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserShopStatusService {

    private final ModelMapper modelMapper;
    private final ShopRepository shopRepository;

    @Transactional
    public UserShopStatusResponse getUserShopStatus(String userSeq) {
        Shop shopStatus = shopRepository.getUserShopStatus(CustomUserUtils.getCurrentUserSeq());

        UserShopStatusResponse response = new UserShopStatusResponse();
        if (shopStatus != null) {
            response.setShopApprStatus(shopStatus.getShopApprStatus());
        } else {
            response.setShopApprStatus(null);
        }

        return response;
    }

}
