package org.wasim.paymentservice.kafka;
import lombok.Data;

@Data
public class OrderConfirmation {
    private String orderId;
    private String userId;
    private String restaurantId;
    private double totalAmount;
}
