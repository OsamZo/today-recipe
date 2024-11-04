package com.goruna.spring.shop.service;

import com.goruna.spring.shop.dto.AdminCategoryRequestDTO;
import com.goruna.spring.shop.entity.ShopCategory;
import com.goruna.spring.shop.repository.ShopCategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminCategoryService {

    private final ShopCategoryRepository shopCategoryRepository;
    private final ModelMapper modelMapper;

    //카테고리 등록
    @Transactional
    public AdminCategoryRequestDTO createCategory(@Valid AdminCategoryRequestDTO adminCategoryRequestDTO) {
        ShopCategory shopCategory = modelMapper.map(adminCategoryRequestDTO, ShopCategory.class);
        shopCategoryRepository.save(shopCategory);
        return adminCategoryRequestDTO;
    }
}
