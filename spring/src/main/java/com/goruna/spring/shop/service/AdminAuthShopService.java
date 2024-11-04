package com.goruna.spring.shop.service;

import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.shop.dto.AdminAuthShopDetailResponseDTO;
import com.goruna.spring.shop.dto.AdminAuthShopResponseDTO;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.AdminAuthShopDetailRepository;
import com.goruna.spring.shop.repository.AdminAuthShopRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminAuthShopService {

    private final AdminAuthShopRepository adminAuthShopRepository;
    private final AdminAuthShopDetailRepository adminAuthShopDetailRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<AdminAuthShopResponseDTO> getAdminAuthAllShop() {
        List<Shop> Shops = adminAuthShopRepository.findAll();
        return Shops.stream()
                .map(shop -> modelMapper.map(shop, AdminAuthShopResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public AdminAuthShopDetailResponseDTO getAdminAuthShopDetail(@Valid Long shopSeq) {
        Shop shop = adminAuthShopDetailRepository.findById(shopSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.SHOP_NOT_FOUND));
        return modelMapper.map(shop, AdminAuthShopDetailResponseDTO.class);
    }
}
