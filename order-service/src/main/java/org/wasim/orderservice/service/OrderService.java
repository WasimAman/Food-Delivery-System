package org.wasim.orderservice.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.wasim.orderservice.clients.MenuServiceClient;
import org.wasim.orderservice.clients.RestaurantServiceClient;
import org.wasim.orderservice.clients.UserServiceClient;
import org.wasim.orderservice.dto.ApiResponse;
import org.wasim.orderservice.dto.OrderItemDto;
import org.wasim.orderservice.dto.response.*;
import org.wasim.orderservice.entity.Order;
import org.wasim.orderservice.entity.OrderItem;
import org.wasim.orderservice.entity.OrderStatus;
import org.wasim.orderservice.exception.OrderNotFoundException;
import org.wasim.orderservice.kafka.OrderConfirmation;
import org.wasim.orderservice.kafka.OrderProducer;
import org.wasim.orderservice.mapper.Mapper;
import org.wasim.orderservice.repository.OrderItemRepository;
import org.wasim.orderservice.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderProducer orderProducer;
    private final UserServiceClient userServiceClient;
    private final RestaurantServiceClient restaurantServiceClient;
    private final MenuServiceClient menuServiceClient;

    @Transactional
    public ApiResponse createOrder(String userId, List<OrderItemDto> orderItemDtos) {

        // ================================
        // 1️⃣ Validate Request
        // ================================
        if (orderItemDtos == null || orderItemDtos.isEmpty()) {
            throw new IllegalArgumentException("Order items cannot be empty");
        }

        // ================================
        // 2️⃣ Validate User
        // ================================
        UserSummaryDto userSummary =
                userServiceClient.getUserSummary(userId);
        System.out.println("User summary: "+ userSummary);
        if (userSummary == null) {
            throw new RuntimeException("User not found");
        }

        // ================================
        // 3️⃣ Validate Single Restaurant Rule
        // ================================
        String restaurantId = orderItemDtos.get(0).getRestaurantId();

        for (OrderItemDto dto : orderItemDtos) {
            if (!restaurantId.equals(dto.getRestaurantId())) {
                throw new IllegalArgumentException(
                        "All items must belong to same restaurant");
            }
        }

        // ================================
        // 4️⃣ Validate Restaurant
        // ================================
        RestaurantSummaryDto restaurantSummary =
                restaurantServiceClient.getRestaurantSummary(restaurantId);

        if (restaurantSummary == null) {
            throw new RuntimeException("Restaurant not found");
        }

        // ================================
        // 5️⃣ Fetch Menu Items (Bulk)
        // ================================
        List<String> menuItemIds = new ArrayList<>();
        for (OrderItemDto dto : orderItemDtos) {
            menuItemIds.add(dto.getMenuItemId());
        }

        List<MenuItemSummaryDto> menuItems =
                menuServiceClient.getMenuItemsBulk(menuItemIds);

        if (menuItems == null || menuItems.isEmpty()) {
            throw new RuntimeException("Menu items not found");
        }

        // Convert to Map<String, MenuItemSummaryDto>
        Map<String, MenuItemSummaryDto> menuItemMap = new HashMap<>();
        for (MenuItemSummaryDto item : menuItems) {
            menuItemMap.put(item.getId(), item);
        }

        // ================================
        // 6️⃣ Calculate Total Amount
        // ================================
        double totalAmount = 0.0;

        for (OrderItemDto dto : orderItemDtos) {

            MenuItemSummaryDto menuItem =
                    menuItemMap.get(dto.getMenuItemId());

            if (menuItem == null) {
                throw new RuntimeException(
                        "Invalid menu item: " + dto.getMenuItemId());
            }

            totalAmount += dto.getPrice() * dto.getQuantity();
        }

        // ================================
        // 7️⃣ Create Order
        // ================================
        Order order = new Order();
        order.setUserId(userId);
        order.setRestaurantId(restaurantId);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        order.setCreatedAt(LocalDateTime.now());

        // ================================
        // 8️⃣ Create Order Items
        // ================================
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDto dto : orderItemDtos) {

            MenuItemSummaryDto menuItem =
                    menuItemMap.get(dto.getMenuItemId());

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItemId(dto.getMenuItemId());
            orderItem.setItemName(menuItem.getName()); // snapshot
            orderItem.setQuantity(dto.getQuantity());
            orderItem.setPrice(dto.getPrice());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        // ================================
        // 9️⃣ Save Order
        // ================================
        Order savedOrder = orderRepository.save(order);

        // ================================
        // 🔟 Kafka Event
        // ================================
        OrderConfirmation event = new OrderConfirmation();
        event.setOrderId(savedOrder.getId());
        event.setUserId(savedOrder.getUserId());
        event.setRestaurantId(savedOrder.getRestaurantId());
        event.setTotalAmount(savedOrder.getTotalAmount());

        orderProducer.sendOrderConfirmation(event);

        // ================================
        // 1️⃣1️⃣ Build Response
        // ================================
        OrderResponseDto responseDto = getOrderResponseDto(savedOrder, userSummary, restaurantSummary);

        return new ApiResponse(
                "Order created successfully. Waiting for payment.",
                responseDto
        );
    }

    private static OrderResponseDto getOrderResponseDto(Order savedOrder, UserSummaryDto userSummary, RestaurantSummaryDto restaurantSummary) {
        List<OrderItemResponseDto> itemResponses = new ArrayList<>();

        for (OrderItem item : savedOrder.getOrderItems()) {

            OrderItemResponseDto itemDto =
                    new OrderItemResponseDto();

            itemDto.setMenuItemId(item.getMenuItemId());
            itemDto.setItemName(item.getItemName());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPrice(item.getPrice());

            itemResponses.add(itemDto);
        }

        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setOrderId(savedOrder.getId());
        responseDto.setStatus(savedOrder.getStatus().name());
        responseDto.setTotalAmount(savedOrder.getTotalAmount());
        responseDto.setUser(userSummary);
        responseDto.setRestaurant(restaurantSummary);
        responseDto.setItems(itemResponses);
        return responseDto;
    }

    public ApiResponse getOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Order not found with id: "+orderId));

        UserSummaryDto userSummary =
                userServiceClient.getUserSummary(order.getUserId());
        System.out.println("User summary: "+ userSummary);
        if (userSummary == null) {
            throw new RuntimeException("User not found");
        }

        RestaurantSummaryDto restaurantSummary =
                restaurantServiceClient.getRestaurantSummary(order.getRestaurantId());

        if (restaurantSummary == null) {
            throw new RuntimeException("Restaurant not found");
        }
        OrderResponseDto orderResponseDto = getOrderResponseDto(order,userSummary,restaurantSummary);
        return new ApiResponse("Order fetched successfully!",orderResponseDto);
    }



    public ApiResponse getUserAllOrders(String userId) {
        List<OrderResponseDto> responseDtos = new ArrayList<>();
        List<Order> orders = orderRepository.findByUserId(userId);
        for (Order order : orders){
            responseDtos.add(Mapper.toOrderResponseDto(order));
        }
        return new ApiResponse("Orders fetched successfully!",responseDtos);
    }

    public ApiResponse updateOrderStatus(String orderId, OrderStatus newStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new OrderNotFoundException("Order not found"));

        // 🔥 Validate Status Transition
        if (!isValidStatusTransition(order.getStatus(), newStatus)) {
            return new ApiResponse(
                    "Invalid status transition from "+ order.getStatus() + " to " + newStatus,
                    null
            );
        }

        order.setStatus(newStatus);
        orderRepository.save(order);

        return new ApiResponse("Order status updated successfully", order);
    }

    private boolean isValidStatusTransition(OrderStatus current, OrderStatus next) {

        if (current == OrderStatus.PENDING_PAYMENT &&
                next == OrderStatus.CONFIRMED) {
            return true;
        }

        if (current == OrderStatus.CONFIRMED &&
                next == OrderStatus.PREPARING) {
            return true;
        }

        if (current == OrderStatus.PREPARING &&
                next == OrderStatus.OUT_FOR_DELIVERY) {
            return true;
        }

        if (current == OrderStatus.OUT_FOR_DELIVERY &&
                next == OrderStatus.DELIVERED) {
            return true;
        }

        if (current != OrderStatus.DELIVERED &&
                next == OrderStatus.CANCELLED) {
            return true;
        }

        return false;
    }
}
