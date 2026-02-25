package org.wasim.orderservice.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.wasim.orderservice.entity.Order;
import org.wasim.orderservice.entity.OrderStatus;
import org.wasim.orderservice.kafka.PaymentConfirmation;
import org.wasim.orderservice.repository.OrderRepository;

@Component
@AllArgsConstructor
public class PaymentConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "payment-topic", groupId = "payment-group")
    public void listenPaymentConfirmation(PaymentConfirmation confirmation) {
        System.out.println("Consuming payment confirmation message from kafka!");
        Order order = orderRepository
                .findById(confirmation.getOrderId())
                .orElseThrow();

        if (confirmation.isSuccess()) {
            order.setStatus(OrderStatus.CONFIRMED);
        } else {
            order.setStatus(OrderStatus.CANCELLED);
        }
        System.out.println("Consumed payment confirmation message from kafka!");
        orderRepository.save(order);
    }
}