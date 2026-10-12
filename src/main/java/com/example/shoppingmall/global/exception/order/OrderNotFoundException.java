package com.example.shoppingmall.global.exception.order;

import com.example.shoppingmall.global.exception.BusinessException;
import com.example.shoppingmall.global.exception.ErrorCode;

public class OrderNotFoundException extends BusinessException {
    public OrderNotFoundException() {
        super(ErrorCode.ORDER_NOT_FOUND);
    }
}
