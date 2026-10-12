package com.example.shoppingmall.global.exception.cart;

import com.example.shoppingmall.global.exception.BusinessException;
import com.example.shoppingmall.global.exception.ErrorCode;

public class CartNotFoundException extends BusinessException {
    public CartNotFoundException() {
        super(ErrorCode.CART_NOT_FOUND);
    }
}
