package com.goruna.spring.shop.service;

import com.goruna.spring.shop.dto.ShopListReadResDTO;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;

    public List<ShopListReadResDTO> readShopsByCategory(Long categorySeq) {
        List<Shop> shops = shopRepository.readShopsByCategory(categorySeq);
        return shops.stream()
                .map(shop -> {
                    ShopListReadResDTO shopListReadResDTO = modelMapper.map(shop, ShopListReadResDTO.class);
                    shopListReadResDTO.setCategoryName(shop.getShopCategory().getCategoryName()); // 카테고리 이름 추가
                    return shopListReadResDTO;
                })
                .collect(Collectors.toList());
    }
}