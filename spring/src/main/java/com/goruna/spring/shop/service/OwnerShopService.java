package com.goruna.spring.shop.service;

import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.shop.dto.UpdateShopInfoDTO;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.OwnerShopRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerShopService {

    private final OwnerShopRepository ownerShopRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void updateShopInfo(Long shopSeq, UpdateShopInfoDTO updateShopInfoDTO) {

        Shop shopInfo = ownerShopRepository.findById(shopSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        modelMapper.map(updateShopInfoDTO, shopInfo);
    }

}
