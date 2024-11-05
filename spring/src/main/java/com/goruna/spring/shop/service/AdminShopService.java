package com.goruna.spring.shop.service;

import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.shop.dto.AdminShopAuthImgDTO;
import com.goruna.spring.shop.dto.AdminShopResponseDTO;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.ShopRepository;
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
public class AdminShopService {

    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;

    // 매장 데이터 전체 조회
    @Transactional
    public List<AdminShopResponseDTO> getAdminAllSearch(Integer page, Integer size) {

        if(page < 1 || size < 1) {
            throw new CustomException(ErrorCodeType.INVALID_VALUE);
        }

        int offset = (page - 1) * size;

        Pageable pageable = PageRequest.of(offset, size);
        Page<Shop> shopsPage = shopRepository.findAll(pageable);
        List<Shop> Shops = shopsPage.getContent();

        return Shops.stream()
                .map(shop -> modelMapper.map(shop, AdminShopResponseDTO.class))
                .collect(Collectors.toList());
    }

    // 사업자 등록증 조회
    public AdminShopAuthImgDTO getShopAuthImg(Long shopSeq) {

        Shop shopAuthImg = shopRepository.findById(shopSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.SHOP_NOT_FOUND));

        return modelMapper.map(shopAuthImg, AdminShopAuthImgDTO.class);

    }
}
