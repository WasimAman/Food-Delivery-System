package org.wasim.paymentservice.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.wasim.paymentservice.entity.Payment;
import org.wasim.paymentservice.entity.PaymentStatus;
import org.wasim.paymentservice.mapper.Mapper;
import org.wasim.paymentservice.repository.PaymentRepository;

@Component
@AllArgsConstructor
public class OrderConsumer {

    private final PaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;

    @KafkaListener(topics = "order-placed",groupId = "order-group")
    public void listenOrderConfirmation(OrderPlacedDto orderPlaced){
        System.out.println("Consuming order placed message from kafka!");
         Payment payment = Mapper.toPaymentModel(orderPlaced);
         Payment savedPayment = paymentRepository.save(payment);

         // here start payment and gey the transection id......

        savedPayment.setStatus(PaymentStatus.SUCCESS);
        savedPayment.setTransactionId("Dummy transection id.....");

        paymentRepository.save(savedPayment);
        System.out.println("Consumed order confirmation message from kafka!");

        // once the payment is done produce a message to the kafka
        paymentProducer.sendPaymentConfirmation(
                new PaymentConfirmation(
                        savedPayment.getOrderId(),
                        true,
                        orderPlaced.getEmail()
                )
        );
    }
}
