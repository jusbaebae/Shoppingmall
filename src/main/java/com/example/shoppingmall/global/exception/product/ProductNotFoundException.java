package com.example.shoppingmall.global.exception.product;

import com.example.shoppingmall.global.exception.BusinessException;
import com.example.shoppingmall.global.exception.ErrorCode;

public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException() {
        super(ErrorCode.PRODUCT_NOT_FOUND);
    }
}
