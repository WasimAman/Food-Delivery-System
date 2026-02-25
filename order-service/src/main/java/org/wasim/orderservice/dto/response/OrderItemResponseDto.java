package org.wasim.orderservice.dto.response;

import lombok.Data;

@Data
public class OrderItemResponseDto {

    private String menuItemId;
    private String itemName;        // populated via menu-service
    private Integer quantity;
    private Double price;
}