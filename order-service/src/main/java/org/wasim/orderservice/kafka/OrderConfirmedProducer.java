package org.wasim.orderservice.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderConfirmedProducer {

    private final KafkaTemplate<String,OrderConfirmation> kafkaTemplate;

    public void sendOrderCreatedConfirmation(OrderConfirmation orderConfirmation){
        kafkaTemplate.send("order-confirmed",orderConfirmation);
    }
}
