package com.example.shoppingmall.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentVerifyRequest {
    @NotNull
    private String paymentId;

    @NotNull
    private Long orderId;
}
