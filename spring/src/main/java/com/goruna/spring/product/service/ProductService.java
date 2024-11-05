package com.goruna.spring.product.service;

import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.product.dto.CreateProductReqDTO;
import com.goruna.spring.product.entity.Product;
import com.goruna.spring.product.repository.ProductRepository;
import com.goruna.spring.shop.repository.OwnerShopRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final OwnerShopRepository ownerShopRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    // 상품 등록
    @Transactional
    public void createProductInfo(CreateProductReqDTO createProductReqDTO) {

//        Long userSeq = CustomUserUtils.getCurrentUserSeq();   // 토큰에서 추출
        Long userSeq = 1019L;
        Long shopSeq = ownerShopRepository.findById(userSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND)).getShopSeq();

        createProductReqDTO.setShopSeq(shopSeq);
        Product product = modelMapper.map(createProductReqDTO, Product.class);

        productRepository.save(product);
    }
}
