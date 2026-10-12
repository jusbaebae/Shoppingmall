package com.example.shoppingmall.global.exception.member;

import com.example.shoppingmall.global.exception.BusinessException;
import com.example.shoppingmall.global.exception.ErrorCode;

public class PasswordMismatchException extends BusinessException {
    public PasswordMismatchException(){
        super(ErrorCode.PASSWORD_MISMATCH);
    }
}
