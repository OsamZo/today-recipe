package com.goruna.spring.shop.service;

import com.goruna.spring.shop.dto.AdminSearchResponseDTO;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.AdminSearchRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminSearchService {

    private final AdminSearchRepository adminSearchRepository;
    private final ModelMapper modelMapper;

    // 매장 데이터 전체 조회
    @Transactional
    public List<AdminSearchResponseDTO> getAdminAllSearch() {
        List<Shop> Shops = adminSearchRepository.findAll();
        return Shops.stream()
                .map(shop -> modelMapper.map(shop, AdminSearchResponseDTO.class))
                .collect(Collectors.toList());
    }
}
