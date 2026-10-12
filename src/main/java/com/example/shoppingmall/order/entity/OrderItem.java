package com.example.shoppingmall.order.entity;

import com.example.shoppingmall.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private int quantity;

    private int orderPrice;

    @Builder
    public OrderItem(Order order, Product product, int quantity, int orderPrice){
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
    }

    public void changeOrder(Order order){
        this.order = order;
    }
}
