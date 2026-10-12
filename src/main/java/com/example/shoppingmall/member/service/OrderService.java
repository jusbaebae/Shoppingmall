package com.example.shoppingmall.member.service;

import com.example.shoppingmall.global.exception.product.ProductNotFoundException;
import com.example.shoppingmall.member.dto.OrderItemRequest;
import com.example.shoppingmall.member.dto.OrderRequest;
import com.example.shoppingmall.member.dto.OrderResponse;
import com.example.shoppingmall.member.entity.*;
import com.example.shoppingmall.global.exception.member.MemberNotFoundException;
import com.example.shoppingmall.global.exception.order.OrderNotFoundException;
import com.example.shoppingmall.member.repository.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PaymentService paymentService;

    @Transactional
    public OrderResponse createOrder(Long memberId,OrderRequest request){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        Order order = Order.builder()
                .member(member)
                .status(OrderStatus.PENDING)
                .receiverName(request.getReceiverName())
                .phone(request.getPhone())
                .zipCode(request.getZipCode())
                .address(request.getAddress())
                .detailAddress(request.getDetailAddress())
                .deliveryRequest(request.getDeliveryRequest())
                .paymentMethod(request.getPaymentMethod())
                .build();

        for(OrderItemRequest itemRequest : request.getOrderItems()){
            Product product = productRepository
                    .findById(itemRequest.getProductId())
                    .orElseThrow(ProductNotFoundException::new);

            product.validateStock(itemRequest.getQuantity());

            int orderPrice = product.getSalePrice() != null ? product.getSalePrice() : product.getPrice();

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .orderPrice(orderPrice)
                    .build();

            order.addOrderItem(orderItem);

            product.removeStock(itemRequest.getQuantity());
        }

        orderRepository.save(order);

        return new OrderResponse(order);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long memberId, Long orderId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        Order order = orderRepository.findByIdAndMember(orderId, member).orElseThrow(OrderNotFoundException::new);

        if (!order.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException(
                    "본인의 주문만 조회할 수 있습니다."
            );
        }

        return new OrderResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrders(Long memberId){
        List<Order> orders = orderRepository.findAllByMemberId(memberId);

        return orders.stream().map(OrderResponse::new).toList();
    }

    @Transactional
    public void cancelOrder(Long memberId, Long orderId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        Order order = orderRepository.findByIdAndMember(orderId, member).orElseThrow(OrderNotFoundException::new);

        order.cancel();

        //주문이 취소되면 상품 재고 원상복구
        for(OrderItem orderItem : order.getOrderItems()){
            Product product = orderItem.getProduct();

            product.addStock(orderItem.getQuantity());
        }
    }

    @Transactional
    public void refundOrder(Long memberId, Long orderId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        Order order = orderRepository.findByIdAndMember(orderId, member).orElseThrow(OrderNotFoundException::new);

        //PortOne 실제 결제 취소
        paymentService.refundPayment(order.getPaymentId());

        order.refund();

        //환불할때도 재고 원상복구
        for(OrderItem orderItem : order.getOrderItems()){
            Product product = orderItem.getProduct();

            product.addStock(orderItem.getQuantity());
        }
    }

    @Transactional
    public OrderResponse payOrder(Long memberId, Long orderId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        Order order = orderRepository.findByIdAndMember(orderId, member).orElseThrow(OrderNotFoundException::new);

        order.pay();

        return new OrderResponse(order);
    }

    @Transactional
    public OrderResponse startShipping(Long memberId, Long orderId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        Order order = orderRepository.findByIdAndMember(orderId, member).orElseThrow(OrderNotFoundException::new);

        order.startShipping();

        return new OrderResponse(order);
    }

    @Transactional
    public OrderResponse completeOrder(Long memberId,Long orderId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        Order order = orderRepository.findByIdAndMember(orderId, member).orElseThrow(OrderNotFoundException::new);

        order.complete();

        return new OrderResponse(order);
    }
}
