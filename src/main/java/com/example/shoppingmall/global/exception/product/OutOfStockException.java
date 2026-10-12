package com.example.shoppingmall.global.exception.product;

import com.example.shoppingmall.global.exception.BusinessException;
import com.example.shoppingmall.global.exception.ErrorCode;

public class OutOfStockException extends BusinessException {
    public OutOfStockException() {
        super(ErrorCode.OUT_OF_STOCK);
    }
}
