package com.example.shoppingmall.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //멤버 관련 예외처리
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    INVALID_LOGIN(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),
    EMAIL_DUPLICATE(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    PASSWORD_MISMATCH(HttpStatus.CONFLICT, "패스워드가 서로 일치하지 않습니다."),

    //상품 관련 예외처리
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "재고가 부족합니다."),
    SOLD_OUT(HttpStatus.BAD_REQUEST, "품절된 상품입니다."),

    //장바구니 관련 예외처리
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."),

    //주문 관련 예외처리
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "조회된 주문이 없습니다."),
    ALREADY_CANCELED(HttpStatus.CONFLICT, "이미 취소된 주문입니다.");

    private final HttpStatus status;
    private final String message;
}
