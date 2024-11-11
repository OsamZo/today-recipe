package com.goruna.spring.product;

import com.goruna.spring.product.dto.CreateProductReqDTO;
import com.goruna.spring.product.dto.CurrentProductResDTO;
import com.goruna.spring.product.entity.Product;
import com.goruna.spring.product.repository.ProductRepository;
import com.goruna.spring.product.service.ProductService;
import com.goruna.spring.shop.repository.ShopRepositoryCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ShopRepositoryCustom shopRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_ShouldSaveProduct() {
        // DTO에 기본적인 값 설정
        CreateProductReqDTO createProductReqDTO = new CreateProductReqDTO();
        createProductReqDTO.setProductName("Sample Product");
        createProductReqDTO.setShopSeq(1L);
        createProductReqDTO.setProductOriginalPrice(1000);
        createProductReqDTO.setProductSalePrice(800);
        createProductReqDTO.setProductQty(10);
        createProductReqDTO.setProductClosedAt(LocalDateTime.now().plusDays(10));
        createProductReqDTO.setProductImgUrl("http://example.com/image.jpg");

        // Mock 객체 반환값 설정
        when(shopRepository.findShopByUserSeq(anyLong())).thenReturn(null); // 객체 생성 없이 null 반환
        Product mockProduct = mock(Product.class); // 필요 시 모의 객체 사용
        when(modelMapper.map(createProductReqDTO, Product.class)).thenReturn(mockProduct);

        productService.createProductInfo(createProductReqDTO);

        // 저장 메서드 호출 검증
        verify(productRepository, times(1)).save(mockProduct);
    }

    @Test
    void getCurrentProduct_ShouldReturnCurrentProductResDTO() {
        Product mockProduct = mock(Product.class); // 필요 시 모의 객체 사용
        when(productRepository.findCurrentProductByUserSeq(anyLong())).thenReturn(mockProduct);
        CurrentProductResDTO mockResponse = mock(CurrentProductResDTO.class);
        when(modelMapper.map(mockProduct, CurrentProductResDTO.class)).thenReturn(mockResponse);

        CurrentProductResDTO response = productService.getCurrentProduct();

        assertNotNull(response);
        verify(productRepository, times(1)).findCurrentProductByUserSeq(anyLong());
    }
}
z