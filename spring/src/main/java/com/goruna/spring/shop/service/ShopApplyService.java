package com.goruna.spring.shop.service;

import com.goruna.spring.shop.dto.ShopApplyReqDTO;
import com.goruna.spring.shop.entity.Shop;
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
    private final ModelMapper modelMapper;

    @Transactional
    public void registerShop(Long userSeq, ShopApplyReqDTO shopApplyReqDTO) {

        User user = userRepository.findById(userSeq)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Shop shop = modelMapper.map(shopApplyReqDTO, Shop.class);
        shop.addUser(user);

        shopRepository.save(shop);
    }
}
