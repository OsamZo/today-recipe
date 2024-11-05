package com.goruna.spring.shop.service;

import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.shop.dto.OnwerShopInfoResDTO;
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

    // 내 매장 정보 수정
    @Transactional
    public void updateShopInfo(Long shopSeq, UpdateShopInfoDTO updateShopInfoDTO) {

        Shop shopInfo = ownerShopRepository.findById(shopSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        modelMapper.map(updateShopInfoDTO, shopInfo);
    }

    // 내 매장 삭제
    @Transactional
    public void deleteShop(Long shopSeq) {

        ownerShopRepository.deleteById(shopSeq);
    }

    // 내 매장 정보 조회
    @Transactional
    public OnwerShopInfoResDTO getOnwerShopInfo() {

//        Long userSeq = CustomUserUtils.getCurrentUserSeq();   // 토큰에서 추출
        Long userSeq = 1019L;
        Shop shop = ownerShopRepository.findById(userSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));
        return modelMapper.map(shop, OnwerShopInfoResDTO.class);
    }
}
