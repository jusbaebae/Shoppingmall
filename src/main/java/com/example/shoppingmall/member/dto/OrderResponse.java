package com.example.shoppingmall.member.dto;

import com.example.shoppingmall.member.entity.Order;
import com.example.shoppingmall.member.entity.OrderStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderResponse {
    private Long orderId;

    private OrderStatus status;

    private int totalPrice;

    private List<OrderItemResponse> items;

    public OrderResponse(Order order) {

        this.orderId = order.getId();
        this.status = order.getStatus();

        this.items = order.getOrderItems()
                .stream()
                .map(OrderItemResponse::new)
                .toList();

        this.totalPrice = order.getOrderItems()
                .stream()
                .mapToInt(item ->
                        item.getOrderPrice() * item.getQuantity()
                )
                .sum();
    }
}
