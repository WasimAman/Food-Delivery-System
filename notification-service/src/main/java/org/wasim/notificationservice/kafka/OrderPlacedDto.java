package org.wasim.notificationservice.kafka;
import lombok.Data;

@Data
public class OrderPlacedDto {
    private String orderId;
    private String userId;
    private String restaurantId;
    private double totalAmount;
    private String email;
}
