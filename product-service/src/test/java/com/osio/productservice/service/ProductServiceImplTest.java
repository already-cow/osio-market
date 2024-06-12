package com.osio.productservice.service;

import com.osio.productservice.dto.ProductDTO;
import com.osio.productservice.entity.Product;
import com.osio.productservice.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("제품 저장 테스트")
    @Transactional
    @Rollback
    void productSave() {
        // Given
        ProductDTO productDTO = ProductDTO.builder()
                .productId(1L)
                .productName("사과")
                .productCategory("과일")
                .productPrice(1000L)
                .productDetail("사과")
                .productImage("사과이미지")
                .build();

        // When
        productService.productAdd(productDTO);
        Product savedProduct = productRepository.findById(1L).orElse(null);

        // Then
        assertNotNull(savedProduct, "제품이 저장되어야 합니다.");
        assertEquals(productDTO.getProductId(), savedProduct.getProductId(), "제품 ID가 일치해야 합니다.");
        assertEquals(productDTO.getProductName(), savedProduct.getProductName(), "제품 이름이 일치해야 합니다.");
        assertEquals(productDTO.getProductCategory(), savedProduct.getProductCategory(), "제품 카테고리가 일치해야 합니다.");
        assertEquals(productDTO.getProductPrice(), savedProduct.getProductPrice(), "제품 가격이 일치해야 합니다.");
        assertEquals(productDTO.getProductDetail(), savedProduct.getProductDetail(), "제품 설명이 일치해야 합니다.");
        assertEquals(productDTO.getProductImage(), savedProduct.getProductImage(), "제품 이미지가 일치해야 합니다.");
    }
}
