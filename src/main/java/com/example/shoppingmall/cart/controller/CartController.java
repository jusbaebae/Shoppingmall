package com.example.shoppingmall.cart.controller;

import com.example.shoppingmall.cart.dto.CartQuantityRequest;
import com.example.shoppingmall.cart.dto.CartRequest;
import com.example.shoppingmall.cart.dto.CartResponse;
import com.example.shoppingmall.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    //장바구니 담기
    @PostMapping
    public CartResponse addCart(@Valid @RequestBody CartRequest request, Authentication authentication){
        Long memberId = (Long) authentication.getPrincipal();

        return cartService.addCart(memberId, request);
    }

    //장바구니 조회
    @GetMapping
    public List<CartResponse> getCart(Authentication authentication){
        Long memberId = (Long) authentication.getPrincipal();

        return cartService.getCart(memberId);
    }

    //장바구니 수량변경
    @PatchMapping("/{cartId}")
    public CartResponse updateQuantity(
            @PathVariable Long cartId,
            @Valid @RequestBody CartQuantityRequest request,
            Authentication authentication
    ){
        Long memberId = (Long) authentication.getPrincipal();

        return cartService.updateQuantity(memberId,cartId,request);
    }

    //장바구니 삭제
    @DeleteMapping("/{cartId}")
    public void deleteCart(@PathVariable Long cartId, Authentication authentication){
        Long memberId = (Long) authentication.getPrincipal();

        cartService.deleteCart(memberId,cartId);
    }

    //장바구니 전체 삭제
    @DeleteMapping("/all")
    public void deleteAllCart(Authentication authentication){
        Long memberId = (Long) authentication.getPrincipal();

        cartService.deleteAllCart(memberId);
    }
}
