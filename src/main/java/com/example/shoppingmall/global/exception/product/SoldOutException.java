package com.example.shoppingmall.global.exception.product;

import com.example.shoppingmall.global.exception.BusinessException;
import com.example.shoppingmall.global.exception.ErrorCode;

public class SoldOutException extends BusinessException {
    public SoldOutException() {
        super(ErrorCode.SOLD_OUT);
    }
}
