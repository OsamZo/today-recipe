package com.goruna.spring.shop.service;

import com.goruna.spring.common.aggregate.ShopApprStatus;
import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.shop.dto.AdminAuthShopDetailResponseDTO;
import com.goruna.spring.shop.dto.AdminAuthShopResponseDTO;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.ShopRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminAuthShopService {

    private final ModelMapper modelMapper;
    private final ShopRepository shopRepository;

    // 매장 등록 인증 전체 조회
    @Transactional
    public List<AdminAuthShopResponseDTO> getAdminAuthAllShop(Integer page, Integer size) {

        if(page < 1 || size < 1){
            throw new CustomException(ErrorCodeType.INVALID_VALUE);
        }

        int offset = (page - 1) * size;

        Pageable pageable = PageRequest.of(offset, size);
        Page<Shop> authShopsPage = shopRepository.findAll(pageable);
        List<Shop> Shops = authShopsPage.getContent();

        return Shops.stream()
                .map(shop -> modelMapper.map(shop, AdminAuthShopResponseDTO.class))
                .collect(Collectors.toList());
    }

    // 매장 등록 인증 상세 조회
    @Transactional
    public AdminAuthShopDetailResponseDTO getAdminAuthShopDetail(@Valid Long shopSeq) {
        Shop shop = shopRepository.findById(shopSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.SHOP_NOT_FOUND));
        return modelMapper.map(shop, AdminAuthShopDetailResponseDTO.class);
    }

    // 매장 등록 인증 권한 수정
    @Transactional
    public AdminAuthShopResponseDTO updateAuth(Long shopSeq, ShopApprStatus shopApprStatus) {

        Shop shop = shopRepository.findById(shopSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.SHOP_NOT_FOUND));

        if(shopApprStatus == ShopApprStatus.APPROVE){
            shop.approve();
        }else{
            shop.disapprove();
        }

        return modelMapper.map(shop, AdminAuthShopResponseDTO.class);
    }

}
