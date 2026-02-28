package org.wasim.orderservice.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderPlacedDto> kafkaTemplate;

    public void sendOrderPlacedEvent(OrderPlacedDto orderPlaced) {
        System.out.println("Sending order confirmation message to kafka!");
        kafkaTemplate.send("order-placed", orderPlaced);
    }
}
