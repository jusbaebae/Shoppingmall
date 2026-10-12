package com.example.shoppingmall.member.controller;

import com.example.shoppingmall.member.dto.OrderRequest;
import com.example.shoppingmall.member.dto.OrderResponse;
import com.example.shoppingmall.member.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest request, Authentication authentication){
        Long memberId = (Long) authentication.getPrincipal();

        return orderService.createOrder(memberId, request);
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId, Authentication authentication){
        Long memberId = (Long) authentication.getPrincipal();

        return orderService.getOrder(memberId,orderId);
    }

    @GetMapping
    public List<OrderResponse> getOrders(Authentication authentication){
        Long memberId = (Long) authentication.getPrincipal();

        return orderService.getOrders(memberId);
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId, Authentication authentication){
        Long memberId = (Long) authentication.getPrincipal();

        orderService.cancelOrder(memberId, orderId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{orderId}/refund")
    public ResponseEntity<Void> refundOrder(@PathVariable Long orderId, Authentication authentication){
        Long memberId = (Long) authentication.getPrincipal();

        orderService.refundOrder(memberId, orderId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{orderId}/pay")
    public OrderResponse payOrder(@PathVariable Long orderId, Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();

        return orderService.payOrder(memberId,orderId);
    }

    @PatchMapping("/{orderId}/shipping")
    public OrderResponse startShipping(@PathVariable Long orderId,Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();

        return orderService.startShipping(memberId,orderId);
    }

    @PatchMapping("/{orderId}/complete")
    public OrderResponse completeOrder(@PathVariable Long orderId,Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();

        return orderService.completeOrder(memberId,orderId);
    }
}
