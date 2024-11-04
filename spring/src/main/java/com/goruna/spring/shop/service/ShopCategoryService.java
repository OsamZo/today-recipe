package com.goruna.spring.shop.service;

import com.goruna.spring.shop.dto.ShopCategoryReadResDTO;
import com.goruna.spring.shop.entity.ShopCategory;
import com.goruna.spring.shop.repository.ShopCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopCategoryService {

    private final ShopCategoryRepository shopCategoryRepository;
    private final ModelMapper modelMapper;

    public List<ShopCategoryReadResDTO> readAllCategories() {
        List<ShopCategory> shopCategories = shopCategoryRepository.findAll();
        return shopCategories.stream()
                .map(shopCategory -> modelMapper.map(shopCategory, ShopCategoryReadResDTO.class))
                .collect(Collectors.toList());
    }
}