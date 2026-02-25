package org.wasim.orderservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.wasim.orderservice.dto.ApiResponse;
import org.wasim.orderservice.dto.OrderItemDto;
import org.wasim.orderservice.entity.OrderStatus;
import org.wasim.orderservice.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody List<OrderItemDto> orderItemDto) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userId = authentication.getPrincipal().toString();

        ApiResponse response = orderService.createOrder(userId, orderItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable String id) {

        ApiResponse response = orderService.getOrder(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-orders")
    public ResponseEntity<?> getMyOrders() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userId = authentication.getPrincipal().toString();

        ApiResponse response = orderService.getUserAllOrders(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable String id,
            @RequestParam OrderStatus status) {

        ApiResponse response = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(response);
    }
}