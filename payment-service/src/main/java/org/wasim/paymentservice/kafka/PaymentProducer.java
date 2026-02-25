package org.wasim.paymentservice.kafka;

import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String, PaymentConfirmation> kafkaTemplate;

    public void sendPaymentConfirmation(PaymentConfirmation paymentConfirmation) {
        System.out.println("sending payment confirmation message from kafka!");
        kafkaTemplate.send("payment-topic", paymentConfirmation);
    }
}
