package com.example.shoppingmall.order.repository;

import com.example.shoppingmall.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
