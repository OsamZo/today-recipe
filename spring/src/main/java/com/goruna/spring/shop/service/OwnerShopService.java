package com.goruna.spring.shop.service;

import com.goruna.spring.shop.repository.OwnerShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerShopService {

    private final OwnerShopRepository ownerShopRepository;

}
