package org.wasim.paymentservice.mapper;


import org.wasim.paymentservice.entity.Payment;
import org.wasim.paymentservice.entity.PaymentStatus;
import org.wasim.paymentservice.kafka.OrderPlacedDto;

import java.time.LocalDateTime;

public class Mapper {
    public static Payment toPaymentModel(OrderPlacedDto orderPlaced) {
        Payment payment = new Payment();
        payment.setOrderId(orderPlaced.getOrderId());
        payment.setAmount(orderPlaced.getTotalAmount());
        payment.setPaymentTime(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTransactionId(null);

        return payment;
    }
}
