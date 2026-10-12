package com.example.shoppingmall.member.dto;

import com.example.shoppingmall.member.entity.Product;
import com.example.shoppingmall.member.entity.ProductStatus;
import lombok.Getter;

@Getter
public class ProductResponse {
    private Long id;
    private String name;
    private int price;
    private Integer salePrice;
    private int stock;
    private String description;
    private String imageUrl;
    private ProductStatus status;

    public ProductResponse(Product product){

        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.salePrice = product.getSalePrice();
        this.stock = product.getStock();
        this.description = product.getDescription();
        this.imageUrl = product.getImageUrl();
        this.status = product.getStatus();

    }
}
