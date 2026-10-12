package com.example.shoppingmall.member.dto;

import com.example.shoppingmall.member.entity.Cart;
import lombok.Getter;

@Getter
public class CartResponse {

    private Long id;
    private Long productId;
    private String productName;
    private int price;
    private int salePrice;
    private int quantity;
    private String imageUrl;

    public CartResponse(Cart cart){
        this.id = cart.getId();
        this.productId = cart.getProduct().getId();
        this.productName = cart.getProduct().getName();
        this.price = cart.getProduct().getPrice();
        this.salePrice = cart.getProduct().getSalePrice();
        this.quantity = cart.getQuantity();
        this.imageUrl = cart.getProduct().getImageUrl();
    }
}
