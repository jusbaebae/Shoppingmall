package com.example.shoppingmall.member.dto;

import com.example.shoppingmall.member.entity.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "상품명은 필수 항목입니다.")
    private String name;

    @NotNull(message = "가격은 필수 항목입니다.")
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    private int price;

    @Min(value = 0, message = "할인 가격은 0원 이상이어야 합니다.")
    private Integer salePrice;

    @NotNull(message = "재고는 필수 항목입니다.")
    @Min(value = 0, message = "재고는 0개 이상이어야 합니다.")
    private int stock;

    @NotBlank(message = "상품 설명은 필수 항목입니다.")
    private String description;

    @NotNull(message = "판매 상태를 선택해주세요.")
    private ProductStatus status;

}
