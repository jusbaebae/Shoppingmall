package com.example.shoppingmall.member.repository;

import com.example.shoppingmall.member.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
