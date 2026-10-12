package com.example.shoppingmall.cart.service;

import com.example.shoppingmall.global.exception.cart.CartNotFoundException;
import com.example.shoppingmall.cart.dto.CartQuantityRequest;
import com.example.shoppingmall.cart.dto.CartRequest;
import com.example.shoppingmall.cart.dto.CartResponse;
import com.example.shoppingmall.cart.entity.Cart;
import com.example.shoppingmall.member.entity.Member;
import com.example.shoppingmall.product.entity.Product;
import com.example.shoppingmall.global.exception.member.MemberNotFoundException;
import com.example.shoppingmall.global.exception.product.ProductNotFoundException;
import com.example.shoppingmall.global.exception.product.SoldOutException;
import com.example.shoppingmall.cart.repository.CartRepository;
import com.example.shoppingmall.member.repository.MemberRepository;
import com.example.shoppingmall.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CartResponse addCart(Long memberId, CartRequest request)
    {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        Product product = productRepository.findById(request.getProductId()).orElseThrow(ProductNotFoundException::new);

        if (product.getStock() <= 0) {
            throw new SoldOutException();
        }

        Cart cart = cartRepository.findByMemberAndProduct(member,product).orElse(null);

        if(cart != null){

            int totalQuantity = cart.getQuantity() + request.getQuantity();

            if(totalQuantity > product.getStock()){throw new SoldOutException();}

            cart.increaseQuantity(request.getQuantity());

        } else {
            cart = Cart.builder().member(member).product(product).quantity(request.getQuantity()).build();

            cartRepository.save(cart);
        }

        return new CartResponse(cart);
    }

    @Transactional
    public List<CartResponse> getCart(Long memberId){

        List<Cart> carts = cartRepository.findAllByMemberId(memberId);

        return carts.stream()
                .map(CartResponse::new)
                .toList();
    }

    @Transactional
    public CartResponse updateQuantity(Long memberId, Long cartId, CartQuantityRequest request){
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(CartNotFoundException::new);

        if(!cart.getMember().getId().equals(memberId)){
            throw new IllegalArgumentException("본인의 장바구니만 수정할 수 있습니다.");
        }

        cart.updateQuantity(request.getQuantity());

        return new CartResponse(cart);
    }

    @Transactional
    public void deleteCart(Long memberId, Long cartId){
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(CartNotFoundException::new);

        if(!cart.getMember().getId().equals(memberId)){
            throw new IllegalArgumentException("본인의 장바구니만 삭제할 수 있습니다.");
        }

        cartRepository.delete(cart);
    }

    @Transactional
    public void deleteAllCart(Long memberId){
        cartRepository.deleteAllByMemberId(memberId);
    }
}
