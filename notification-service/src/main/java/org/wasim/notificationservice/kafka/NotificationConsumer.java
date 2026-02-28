package org.wasim.notificationservice.kafka;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.wasim.notificationservice.email.EmailService;

@Service
@AllArgsConstructor
public class NotificationConsumer {

    private final EmailService emailService;

    // 1️⃣ ORDER PLACED
//    @KafkaListener(topics = "order-placed", groupId = "notification-group")
//    public void handleOrderPlaced(OrderPlacedDto event) {
//
//        String subject = "Order Placed Successfully 🛒";
//        String body = "Hi,\n\nYour order #" +
//                event.getOrderId() +
//                " has been placed successfully.\nTotal Amount: ₹" +
//                event.getTotalAmount() +
//                "\n\nWe are processing your payment.";
//
//        emailService.sendEmail(event.getEmail(), subject, body);
//    }

    // 2️⃣ PAYMENT SUCCESS
//    @KafkaListener(topics = "payment-success", groupId = "notification-group")
//    public void handlePaymentSuccess(PaymentConfirmation event) {
//
//        if (event.isSuccess()) {
//
//            String subject = "Payment Successful ✅";
//            String body = "Hi,\n\nPayment for order #" +
//                    event.getOrderId() +
//                    " was successful.\n\nYour order is being confirmed.";
//
//            emailService.sendEmail(event.getEmail(), subject, body);
//        } else {
//
//            String subject = "Payment Failed ❌";
//            String body = "Hi,\n\nPayment for order #" +
//                    event.getOrderId() +
//                    " failed.\nPlease try again.";
//
//            emailService.sendEmail(event.getEmail(), subject, body);
//        }
//    }

    // 3️⃣ ORDER CONFIRMED
    @KafkaListener(topics = "order-confirmed", groupId = "notification-group")
    public void handleOrderConfirmed(OrderConfirmation event) {

        String subject = "Order Confirmed 🎉";
        String body = "Hi,\n\nYour order #" +
                event.getOrderId() +
                " has been confirmed.\nTotal Amount: ₹" +
                event.getTotalAmount() +
                "\n\nRestaurant is preparing your food.";

        emailService.sendEmail(event.getEmail(), subject, body);
    }

    // 4️⃣ ORDER CANCELLED
    @KafkaListener(topics = "order-cancelled", groupId = "notification-group")
    public void handleOrderCancelled(OrderConfirmation event) {

        String subject = "Order Cancelled ❌";
        String body = "Hi,\n\nYour order #" +
                event.getOrderId() +
                " has been cancelled.\nIf payment was deducted, refund will be processed.";

        emailService.sendEmail(event.getEmail(), subject, body);
    }
}