package com.example.shoppingmall.product.entity;

import com.example.shoppingmall.global.exception.product.OutOfStockException;
import com.example.shoppingmall.global.exception.product.SoldOutException;
import com.example.shoppingmall.member.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int price;

    private Integer salePrice;

    private int stock;

    private String description;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ProductStatus status = ProductStatus.ON_SALE;

    public void update(String name, int price, Integer salePrice, int stock, String description, String imageUrl, ProductStatus status) {
        this.name = name;
        this.price = price;
        this.salePrice = salePrice;
        this.stock = stock;
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public void validateStock(int quantity) {
        if (status == ProductStatus.SOLD_OUT || stock <= 0) {
            throw new SoldOutException();
        }

        if (quantity > stock) {
            throw new OutOfStockException();
        }
    }

    public void removeStock(int quantity){
        if (stock < quantity) {
            throw new OutOfStockException();
        }

        this.stock -= quantity;

        //재고가 0이 되면 자동 품절
        if (this.stock == 0) {
            this.status = ProductStatus.SOLD_OUT;
        }
    }

    public void addStock(int quantity){
        this.stock += quantity;

        //재고가 다시 생기면 판매중으로 변경
        if (this.stock > 0 && this.status == ProductStatus.SOLD_OUT) {
            this.status = ProductStatus.ON_SALE;
        }
    }

    public void changeStatus(ProductStatus status){
        this.status = status;
    }

    public int getCurrentPrice(){

        if(salePrice != null){
            return salePrice;
        }

        return price;
    }
}
