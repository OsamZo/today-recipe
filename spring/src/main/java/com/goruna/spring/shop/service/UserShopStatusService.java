package com.goruna.spring.shop.service;

import com.goruna.spring.common.aggregate.YnType;
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

        UserShopStatusResponse userShopStatusResponse = new UserShopStatusResponse();

        String status = "";

        if (shopStatus == null) {
            status = "USER";
        } else {
            if (shopStatus.getShopName() == null && shopStatus.getShopApprStatus() == null) {
                status = "USER";
            } else if (shopStatus.getShopName() != null && shopStatus.getShopApprStatus().equals(YnType.N)) {
                status = "SEMIUSER";
            } else if (shopStatus.getShopName() != null && shopStatus.getShopApprStatus().equals(YnType.Y)) {
                status = "OWNER";
                modelMapper.map(shopStatus, userShopStatusResponse);
            }
        }

        userShopStatusResponse.setShopStatus(status);

        return userShopStatusResponse;
    }
}
