package com.goruna.spring.shop.service;

import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.shop.dto.AdminCategoryRequestDTO;
import com.goruna.spring.shop.dto.AdminCategoryResponseDTO;
import com.goruna.spring.shop.entity.ShopCategory;
import com.goruna.spring.shop.repository.ShopCategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public List<AdminCategoryResponseDTO> getAdminAllCategory() {

        List<ShopCategory> shopCategories = shopCategoryRepository.findAll();

        return shopCategories.stream()
                .map(shopCategory -> modelMapper.map(shopCategory, AdminCategoryResponseDTO.class))
                .collect(Collectors.toList());

    }
    @Transactional
    public boolean deleteCategory(Long categorySeq) {
        if(shopCategoryRepository.existsById(categorySeq)) {
            shopCategoryRepository.deleteById(categorySeq);
            return true;
        } else{
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }
    }
}
