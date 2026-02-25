package org.wasim.orderservice.mapper;

import org.wasim.orderservice.dto.response.OrderItemResponseDto;
import org.wasim.orderservice.dto.response.OrderResponseDto;
import org.wasim.orderservice.entity.Order;
import org.wasim.orderservice.entity.OrderItem;

public class Mapper {

    public static OrderResponseDto toOrderResponseDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderId(order.getId());
        orderResponseDto.setTotalAmount(order.getTotalAmount());
        orderResponseDto.setStatus(order.getStatus().name());

        for(OrderItem orderItem : order.getOrderItems()){
            OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();
            orderItemResponseDto.setMenuItemId(orderItem.getMenuItemId());
            orderItemResponseDto.setItemName(orderItem.getItemName());
            orderItemResponseDto.setQuantity(orderItem.getQuantity());
            orderItemResponseDto.setPrice(orderItem.getPrice());

            orderResponseDto.getItems().add(orderItemResponseDto);
        }
        return orderResponseDto;
    }

//    public static OrderItemResponseDto toOrderResponseDto(String userId,
//                                                          List<OrderItemDto> orderItemDto) {
//
//    }

}
