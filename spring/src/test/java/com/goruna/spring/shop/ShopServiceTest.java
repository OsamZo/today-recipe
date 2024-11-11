package com.goruna.spring.shop;

import com.goruna.spring.product.entity.Product;
import com.goruna.spring.product.repository.ProductRepository;
import com.goruna.spring.shop.dto.ShopListReadResDTO;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.entity.ShopCategory;
import com.goruna.spring.shop.repository.ShopRepository;
import com.goruna.spring.shop.service.ShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopServiceTest {

    @InjectMocks
    private ShopService shopService;

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void readShopsAllToday_ShouldReturnShopList() {
        // Arrange
        LocalDateTime startOfDay = LocalDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        // Mock Products
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);

        List<Product> products = Arrays.asList(product1, product2);

        when(shopRepository.readShopsAllToday(startOfDay, endOfDay)).thenReturn(products);

        // Mock DTOs
        ShopListReadResDTO dto1 = new ShopListReadResDTO();
        ShopListReadResDTO dto2 = new ShopListReadResDTO();

        when(modelMapper.map(product1, ShopListReadResDTO.class)).thenReturn(dto1);
        when(modelMapper.map(product2, ShopListReadResDTO.class)).thenReturn(dto2);

        // Mock nested method calls for product1
        Shop shop1 = mock(Shop.class);
        ShopCategory category1 = mock(ShopCategory.class);

        when(product1.getShop()).thenReturn(shop1);
        when(shop1.getShopCategory()).thenReturn(category1);
        when(category1.getCategorySeq()).thenReturn(1L);
        when(category1.getCategoryName()).thenReturn("Category1");

        // Mock nested method calls for product2
        Shop shop2 = mock(Shop.class);
        ShopCategory category2 = mock(ShopCategory.class);

        when(product2.getShop()).thenReturn(shop2);
        when(shop2.getShopCategory()).thenReturn(category2);
        when(category2.getCategorySeq()).thenReturn(2L);
        when(category2.getCategoryName()).thenReturn("Category2");

        // Act
        List<ShopListReadResDTO> result = shopService.readShopsAllToday();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verify that modelMapper.map() was called for each product
        verify(modelMapper).map(product1, ShopListReadResDTO.class);
        verify(modelMapper).map(product2, ShopListReadResDTO.class);

        // Assert that the DTOs have the correct category information
        assertEquals(1L, result.get(0).getCategorySeq());
        assertEquals("Category1", result.get(0).getCategoryName());
        assertEquals(2L, result.get(1).getCategorySeq());
        assertEquals("Category2", result.get(1).getCategoryName());
    }
}
