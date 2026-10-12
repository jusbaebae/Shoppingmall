package com.example.shoppingmall.global.exception.member;

import com.example.shoppingmall.global.exception.BusinessException;
import com.example.shoppingmall.global.exception.ErrorCode;

public class InvalidLoginException extends BusinessException {
    public InvalidLoginException() {
        super(ErrorCode.INVALID_LOGIN);
    }
}
