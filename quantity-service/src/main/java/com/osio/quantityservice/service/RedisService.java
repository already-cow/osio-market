package com.osio.quantityservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setQuantity(Long productId, Long quantity) {
        redisTemplate.opsForValue().set(productId.toString(), quantity.toString());
    }

    public Long getQuantity(Long productId) {
        String quantityString = redisTemplate.opsForValue().get(productId.toString());
        if (quantityString != null) {
            return Long.parseLong(quantityString);
        } else {
            return null;
        }
    }

//    public Map<String, String> getAllKeysAndValuesFromRedis() {
//        // 레디스 모든 키 가져오기
//        Set<String> keys = redisTemplate.keys("*");
//
//        // 각 키에 해당하는 값을 맵에 추가
//        Map<String, String> keyValueMap = new HashMap<>();
//        for (String key : keys) {
//            keyValueMap.put(key, redisTemplate.opsForValue().get(key));
//        }
//
//        return keyValueMap;
//    }

    public ResponseEntity<String> decreaseQuantity(Long productId, Long quantity) {
        Long result = redisTemplate.opsForValue().decrement(productId.toString(), quantity);
        if (result >= 0) {
            return ResponseEntity.ok(quantity.toString());
        } else {
            // 재고가 부족한 경우 음수가 될 수 있음
            return ResponseEntity.badRequest().body("상품 재고가 부족합니다");
        }
    }

    public void increaseQuantity(Long productId, Long quantity) {
        redisTemplate.opsForValue().increment(productId.toString(), quantity);
    }

    // 해당 키,값 삭제
    public void deleteQuantity(Long productId) {
        redisTemplate.delete(productId.toString());
    }
}
