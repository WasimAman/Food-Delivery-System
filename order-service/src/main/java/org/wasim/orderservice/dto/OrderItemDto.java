package org.wasim.orderservice.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private String menuItemId;
    private String restaurantId;
    private Integer quantity;
    private Double price;
}
