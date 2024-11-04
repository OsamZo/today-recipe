package com.goruna.spring.shop.service;

import com.goruna.spring.shop.repository.OwnerShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OwnerShopServiceTest {

    @Autowired
    private OwnerShopService ownerShopService;

    @Autowired
    private OwnerShopRepository ownerShopRepository;

    @DisplayName("(사장) 매장 수정 테스트")
    @Test
    // (사장) 매장 수정 기능
    void updateShopInfoTest() {
        // given

        // when

        // then
    }
}