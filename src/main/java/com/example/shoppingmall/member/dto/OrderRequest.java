package com.example.shoppingmall.member.dto;

import com.example.shoppingmall.member.entity.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderRequest {

    @NotBlank
    private List<OrderItemRequest> orderItems;

    @NotBlank
    private String receiverName;

    @NotBlank
    private String phone;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String address;

    private String detailAddress;

    private String deliveryRequest;

    @NotNull
    private PaymentMethod paymentMethod;
}
