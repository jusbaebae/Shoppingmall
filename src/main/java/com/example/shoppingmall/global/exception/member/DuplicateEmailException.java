package com.example.shoppingmall.global.exception.member;

import com.example.shoppingmall.global.exception.BusinessException;
import com.example.shoppingmall.global.exception.ErrorCode;

public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException(){
        super(ErrorCode.EMAIL_DUPLICATE);
    }
}
