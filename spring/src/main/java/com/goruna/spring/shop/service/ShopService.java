package com.goruna.spring.shop.service;

import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.product.entity.Product;
import com.goruna.spring.product.repository.ProductRepository;
import com.goruna.spring.shop.dto.ShopCardReadResDTO;
import com.goruna.spring.shop.dto.ShopDetailReadResDTO;
import com.goruna.spring.shop.dto.ShopListReadResDTO;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Slf4j
public class ShopService {

    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    // 매장 리스트 조회 시 매장 카테고리 시퀀스와 매장 카테고리 이름을 dto에 set해주는 공통 메소드
    private List<ShopListReadResDTO> getShopListReadResDTOS(List<Product> shops) {
        return shops.stream()
                .map(shop -> {
                    ShopListReadResDTO shopListReadResDTO = modelMapper.map(shop, ShopListReadResDTO.class);
                    shopListReadResDTO.setCategorySeq(shop.getShop().getShopCategory().getCategorySeq());
                    shopListReadResDTO.setCategoryName(shop.getShop().getShopCategory().getCategoryName());
                    return shopListReadResDTO;
                })
                .collect(Collectors.toList());
    }

    // 오늘의 특가 리스트 조회
    public List<ShopListReadResDTO> readLatest5ShopsToday() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        List<Product> shops = shopRepository.readLatest5ShopsToday(startOfDay, endOfDay);
        return getShopListReadResDTOS(shops);
    }

    // 전체 매장 목록 조회
    public List<ShopListReadResDTO> readShopsAllToday() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        List<Product> shops = shopRepository.readShopsAllToday(startOfDay, endOfDay);
        return getShopListReadResDTOS(shops);
    }

    // 카테고리 별 매장 목록 조회
    public List<ShopListReadResDTO> readShopsByCategory(Long categorySeq, Integer page) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        if (page == null || page < 1) {
            throw new CustomException(ErrorCodeType.INVALID_VALUE);
        }

        int pageSize = 6;
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        List<Product> shops = shopRepository.readShopByCategorySeq(categorySeq, pageable, startOfDay, endOfDay);
        return getShopListReadResDTOS(shops);
    }

    // 매장 상세 내역 조회
    public ShopDetailReadResDTO readShopDetail(Long shopSeq) {
        Shop shop = shopRepository.findById(shopSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.SHOP_NOT_FOUND));

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        Product product = productRepository.readLatestProductToday(shopSeq, startOfDay, endOfDay);

        // 해당 매장의 리뷰 개수
        int shopReviewCount = Math.toIntExact(shopRepository.countReviewsByShopSeq(shopSeq));

        ShopDetailReadResDTO shopDetailReadResDTO = modelMapper.map(shop, ShopDetailReadResDTO.class);
        shopDetailReadResDTO.setShopReviewCount(shopReviewCount);
        shopDetailReadResDTO.setProductClosedAt(product.getProductClosedAt());
        shopDetailReadResDTO.setProductSeq(product.getProductSeq());
        shopDetailReadResDTO.setProductName(product.getProductName());
        shopDetailReadResDTO.setProductDescription(product.getProductDescription());
        shopDetailReadResDTO.setProductQty(product.getProductQty());
        shopDetailReadResDTO.setProductOriginalPrice(product.getProductOriginalPrice());
        shopDetailReadResDTO.setProductSalePrice(product.getProductSalePrice());
        shopDetailReadResDTO.setProductImgUrl(product.getProductImgUrl());
        return shopDetailReadResDTO;
    }

    /* 매장 이미지, 매장 이름 조회 API */
    public ShopCardReadResDTO shopCardRead(Long shopSeq){
        Shop shop = shopRepository.findById(shopSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.SHOP_NOT_FOUND));

        return modelMapper.map(shop, ShopCardReadResDTO.class);
    }
}