package org.wasim.paymentservice.mapper;


import org.wasim.paymentservice.entity.Payment;
import org.wasim.paymentservice.entity.PaymentStatus;
import org.wasim.paymentservice.kafka.OrderConfirmation;

import java.time.LocalDateTime;

public class Mapper {
    public static Payment toPaymentModel(OrderConfirmation orderConfirmation) {
        Payment payment = new Payment();
        payment.setOrderId(orderConfirmation.getOrderId());
        payment.setAmount(orderConfirmation.getTotalAmount());
        payment.setPaymentTime(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTransactionId(null);

        return payment;
    }
}
