package org.wasim.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wasim.orderservice.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findByUserId(String userId);
}
