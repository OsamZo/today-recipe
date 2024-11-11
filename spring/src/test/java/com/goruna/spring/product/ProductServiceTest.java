package com.goruna.spring.product;

import com.goruna.spring.common.util.CustomUserUtils;
import com.goruna.spring.product.dto.CurrentProductResDTO;
import com.goruna.spring.product.entity.Product;
import com.goruna.spring.product.repository.ProductRepository;
import com.goruna.spring.product.service.ProductService;
import com.goruna.spring.shop.repository.ShopRepositoryCustom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    // Remove the @Mock annotation from CustomUserUtils
    // We will mock the static method instead

    private Product mockProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock objects
        mockProduct = mock(Product.class);
    }

    @Test
    void getCurrentProduct_ShouldReturnCurrentProductResDTO() {
        // Mock the static method CustomUserUtils.getCurrentUserSeq()
        try (MockedStatic<CustomUserUtils> customUserUtilsMockedStatic = mockStatic(CustomUserUtils.class)) {
            // Mock the return value of the static method
            customUserUtilsMockedStatic.when(CustomUserUtils::getCurrentUserSeq).thenReturn(1L);

            // Mock the repository method
            when(productRepository.findCurrentProductByUserSeq(anyLong())).thenReturn(mockProduct);

            // Mock the ModelMapper mapping
            CurrentProductResDTO mockResponse = new CurrentProductResDTO();
            when(modelMapper.map(any(Product.class), eq(CurrentProductResDTO.class))).thenReturn(mockResponse);

            // Call the method under test
            CurrentProductResDTO response = productService.getCurrentProduct();

            // Assertions and verifications
            assertNotNull(response);
            verify(productRepository, times(1)).findCurrentProductByUserSeq(anyLong());
        }
    }
}
