package com.example.shoppingmall.global.exception.order;

import com.example.shoppingmall.global.exception.BusinessException;
import com.example.shoppingmall.global.exception.ErrorCode;

public class AlreadyCancelledOrderException extends BusinessException {
    public AlreadyCancelledOrderException() {
        super(ErrorCode.ALREADY_CANCELED);
    }
}
