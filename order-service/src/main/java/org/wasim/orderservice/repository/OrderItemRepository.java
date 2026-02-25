package org.wasim.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wasim.orderservice.entity.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,String> {
    List<OrderItem> findByOrderId(String orderId);
}
