package org.wasim.notificationservice.kafka;

import lombok.Data;

@Data
public class OrderConfirmation {
    private String orderId;
    private double totalAmount;
    private String email;
}