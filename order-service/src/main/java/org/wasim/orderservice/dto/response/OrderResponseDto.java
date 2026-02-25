package org.wasim.orderservice.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponseDto {

    private String orderId;
    private String status;
    private Double totalAmount;

    private RestaurantSummaryDto restaurant;
    private UserSummaryDto user;
    private List<OrderItemResponseDto> items = new ArrayList<>();
}