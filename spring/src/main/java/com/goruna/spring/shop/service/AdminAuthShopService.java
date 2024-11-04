package com.goruna.spring.shop.service;

import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.shop.dto.AdminAuthShopResponseDTO;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.AdminAuthShopRepository;
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

    private final AdminAuthShopRepository adminAuthShopRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<AdminAuthShopResponseDTO> getAdminAuthAllShop(Integer page, Integer size) {

        if(page < 1 || size < 1){
            throw new CustomException(ErrorCodeType.INVALID_VALUE);
        }

        int offset = (page - 1) * size;

        Pageable pageable = PageRequest.of(offset, size);
        Page<Shop> authShopsPage = adminAuthShopRepository.findAll(pageable);
        List<Shop> Shops = authShopsPage.getContent();

        return Shops.stream()
                .map(shop -> modelMapper.map(shop, AdminAuthShopResponseDTO.class))
                .collect(Collectors.toList());
    }
}
