package com.osio.quantityservice.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osio.quantityservice.dto.QuantityDTO;
import com.osio.quantityservice.dto.QuantityUpdateDTO;
import com.osio.quantityservice.service.QuantityServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RollBackConsumer {
    private final QuantityServiceImpl quantityServiceImpl;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 0. 재고 확인 및 감소
    @KafkaListener(topics = "check-quantity", groupId = "group-01")
    public void checkQuantity(String kafkaMessage) throws JsonProcessingException {
        log.info("Check quantity: {}", kafkaMessage);

        QuantityDTO quantityDTO = objectMapper.readValue(kafkaMessage, QuantityDTO.class);

        // 1. 재고 확인 및 감소
        ResponseEntity<String> decreaseResponse = quantityServiceImpl.decreaseQuantity(quantityDTO.getProductId(), quantityDTO.getQuantity());

        if (decreaseResponse.getStatusCode() == HttpStatus.OK) {
            log.info("주문이 완료되었습니다.");
        } else {
            log.error(decreaseResponse.getBody());
            log.error("재고가 부족하여 주문을 롤백합니다.");
            quantityServiceImpl.rollBackCreatedOrder(quantityDTO);
        }
    }

    @KafkaListener(topics = "roll-back-quantity", groupId = "group-01")
    public void rollBackQuantity(String kafkaMessage) throws JsonProcessingException {
        log.info("Roll back quantity: {}", kafkaMessage);

        QuantityDTO quantityDTO = objectMapper.readValue(kafkaMessage, QuantityDTO.class);

        quantityServiceImpl.rollBackQuantity(quantityDTO);
    }

    // 3-1 [Error] : 주문 삭제 및 재고 복구
    @KafkaListener(topics = "payment-rollback", groupId = "group-01")
    public void rollback(String kafkaMessage) throws JsonProcessingException {

        QuantityDTO quantityDTO = objectMapper.readValue(kafkaMessage, QuantityDTO.class);

        log.error("======== [Rollback] payment-rollback, orderId: {} =========", quantityDTO.getOrderId());
        // 롤백 메소드
        quantityServiceImpl.rollBackOrderAndQuantity(quantityDTO);
    }

    // 물건 문제 생기면 quantity, redis 삭제
    @KafkaListener(topics = "rollback-product", groupId = "group-01")
    public void rollbackProduct(String kafkaMessage) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        QuantityUpdateDTO quantityUpdateDTO = mapper.readValue(kafkaMessage, QuantityUpdateDTO.class);

        log.error("======== [Rollback] rollback-product");
        // 롤백 메소드
        quantityServiceImpl.rollBackQuantity(quantityUpdateDTO);
    }
}
