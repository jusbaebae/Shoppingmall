package com.example.shoppingmall.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemRequest {
    @NotBlank
    private Long productId;

    @NotBlank
    private int quantity;

    private Long cartId;
}
