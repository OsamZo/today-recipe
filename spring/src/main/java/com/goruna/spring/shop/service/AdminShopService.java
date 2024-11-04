package com.goruna.spring.shop.service;

import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.shop.dto.AdminShopResponseDTO;
import com.goruna.spring.shop.entity.Shop;
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

    private final AdminShopRepository adminSearchRepository;
    private final ModelMapper modelMapper;

    // 매장 데이터 전체 조회
    @Transactional
    public List<AdminShopResponseDTO> getAdminAllSearch(Integer page, Integer size) {

        if(page < 1 || size < 1) {
            throw new CustomException(ErrorCodeType.INVALID_VALUE);
        }

        int offset = (page - 1) * size;

        Pageable pageable = PageRequest.of(offset, size);
        Page<Shop> shopsPage = adminSearchRepository.findAll(pageable);
        List<Shop> Shops = shopsPage.getContent();

        return Shops.stream()
                .map(shop -> modelMapper.map(shop, AdminShopResponseDTO.class))
                .collect(Collectors.toList());
    }
}
