package com.goruna.spring.product.service;

import com.goruna.spring.common.s3.FileUploadUtil;
import com.goruna.spring.common.util.CustomUserUtils;
import com.goruna.spring.product.dto.CreateProductReqDTO;
import com.goruna.spring.product.dto.CurrentProductResDTO;
import com.goruna.spring.product.entity.Product;
import com.goruna.spring.product.repository.ProductRepository;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.ShopRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ShopRepositoryCustom shopRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final FileUploadUtil fileUploadUtil;

    // 상품 등록
    @Transactional
    public void createProductInfo(CreateProductReqDTO createProductReqDTO, String productImg, Long userSeq) {
        Shop shop = shopRepository.findShopByUserSeq(userSeq);

        Product product = modelMapper.map(createProductReqDTO, Product.class);
        product.addShop(shop);
        product.addProductImg(productImg);

        productRepository.save(product);
    }

    public CurrentProductResDTO getCurrentProduct() {

        Long userSeq = CustomUserUtils.getCurrentUserSeq();   // 토큰에서 추출
        Product currentProduct = productRepository.findCurrentProductByUserSeq(userSeq);

        CurrentProductResDTO productResDTO = modelMapper.map(currentProduct, CurrentProductResDTO.class);
        return productResDTO;
    }
}
