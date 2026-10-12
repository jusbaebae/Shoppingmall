package com.example.shoppingmall.cart.entity;

import com.example.shoppingmall.member.entity.BaseEntity;
import com.example.shoppingmall.member.entity.Member;
import com.example.shoppingmall.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private int quantity;

    @Builder
    public Cart(Member member, Product product, int quantity)
    {
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    public void increaseQuantity(int quantity){
        this.quantity += quantity;
    }

    public void updateQuantity(int quantity) {this.quantity = quantity;}
}
