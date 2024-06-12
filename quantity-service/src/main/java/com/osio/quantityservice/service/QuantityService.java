package com.osio.quantityservice.service;

import com.osio.quantityservice.dto.QuantityUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface QuantityService {
    // 재고 확인
    ResponseEntity<String> checkQuantity(Long productId, Long orderProductQuantity, Long availableQuantity);

    // 재고 갑소
    ResponseEntity<String> decreaseQuantity(Long productId, Long orderProductQuantity);

    // 재고 DB 저장
    void updateQuantity(QuantityUpdateDTO quantityUpdateDTO);

    // Schedule
    @Transactional
    void updateDatabaseQuantity();

    Long getProductQuantity(Long productId);
}
