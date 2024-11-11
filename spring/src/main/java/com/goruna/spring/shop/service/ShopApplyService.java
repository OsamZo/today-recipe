package com.goruna.spring.shop.service;

import com.goruna.spring.shop.dto.ShopApplyReqDTO;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.entity.ShopCategory;
import com.goruna.spring.shop.repository.ShopCategoryRepository;
import com.goruna.spring.shop.repository.ShopRepository;
import com.goruna.spring.users.entity.User;
import com.goruna.spring.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopApplyService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ShopCategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void registerShop(Long userSeq,
                             Long categorySeq,
                             ShopApplyReqDTO shopApplyReqDTO,
                             String businessImg,
                             String shopImg) {

        User user = userRepository.findById(userSeq)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShopCategory category = categoryRepository.findById(categorySeq)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Shop shop = modelMapper.map(shopApplyReqDTO, Shop.class);
        shop.addUser(user);
        shop.addCategroy(category);
        shop.addShopBusinessImgUrl(businessImg);
        shop.addShopImgUrl(shopImg);


        shopRepository.save(shop);
    }
}
