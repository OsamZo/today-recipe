package com.goruna.spring.shop.service;

import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.shop.dto.UpdateShopInfoDTO;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.ShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OwnerShopServiceTest {

    @Autowired
    private OwnerShopService ownerShopService;

    @Autowired
    private ShopRepository shopRepository;

    @Transactional
    @DisplayName("(사장) 매장 수정 테스트")
    @Test
    public void testUpdateShopInfo() {
        // given
        Long shopSeq = 1019L;

        UpdateShopInfoDTO updateShopInfoDTO = new UpdateShopInfoDTO();
        updateShopInfoDTO.setShopName("업데이트된 맛집");
        updateShopInfoDTO.setShopAddress("서울특별시 강남구 청담동");
        updateShopInfoDTO.setShopIntroduction("테스트 수정 테스트 수정 테스트 수정");

        // when
        ownerShopService.updateShopInfo(shopSeq, updateShopInfoDTO);

        // then
        Shop updatedShop = shopRepository.findById(shopSeq)
                .orElseThrow(() -> new AssertionError("Shop not found"));

        assertEquals("업데이트된 맛집", updatedShop.getShopName());
        assertEquals("서울특별시 강남구 청담동", updatedShop.getShopAddress());
        assertEquals("테스트 수정 테스트 수정 테스트 수정", updatedShop.getShopIntroduction());
    }

    @Transactional
    @DisplayName("존재하지 않는 ShopSeq 에 대해 에러메세지 발생")
    @Test
    public void testUpdateShopInfo_ShopNotFound() {
        UpdateShopInfoDTO updateShopInfoDTO = new UpdateShopInfoDTO();
        updateShopInfoDTO.setShopName("업데이트된 맛집");
        updateShopInfoDTO.setShopAddress("서울특별시 강남구 청담동");
        updateShopInfoDTO.setShopIntroduction("테스트 수정 테스트 수정 테스트 수정");

        // 존재하지 않는 샵 ID로 업데이트 시도
        CustomException exception = assertThrows(CustomException.class, () -> {
            ownerShopService.updateShopInfo(999L, updateShopInfoDTO);
        });

        assertEquals(ErrorCodeType.DATA_NOT_FOUND, exception.getErrorCode());
    }
}