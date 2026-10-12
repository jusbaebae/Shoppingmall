package com.example.shoppingmall.member.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartRequest {
    private Long productId;

    @Min(value = 1, message = "최소 수량은 1개입니다.")
    private int quantity;
}
