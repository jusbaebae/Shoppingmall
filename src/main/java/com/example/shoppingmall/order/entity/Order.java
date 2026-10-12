package com.example.shoppingmall.order.entity;

import com.example.shoppingmall.global.exception.order.AlreadyCancelledOrderException;
import com.example.shoppingmall.member.entity.BaseEntity;
import com.example.shoppingmall.member.entity.Member;
import com.example.shoppingmall.payment.entity.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String receiverName;

    private String phone;

    private String zipCode;

    private String address;

    private String detailAddress;

    private String deliveryRequest;

    @Setter
    private String paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(
            Member member,
            OrderStatus status,
            String receiverName,
            String phone,
            String zipCode,
            String address,
            String detailAddress,
            String deliveryRequest,
            String paymentId,
            PaymentMethod paymentMethod
    ) {
        this.member = member;
        this.status = status;
        this.receiverName = receiverName;
        this.phone = phone;
        this.zipCode = zipCode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.deliveryRequest = deliveryRequest;
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.changeOrder(this);
    }

    public void cancel(){
        if(this.status == OrderStatus.CANCELED){
            throw new AlreadyCancelledOrderException();
        }

        this.status = OrderStatus.CANCELED;
    }

    public void pay(){
        if(this.status != OrderStatus.PENDING){
            throw new IllegalArgumentException("결제할 수 없는 주문입니다.");
        }
        this.status = OrderStatus.PAID;
    }

    public void refund(){
        if(this.status != OrderStatus.PAID){
            throw new IllegalArgumentException("환불할 수 없는 주문입니다.");
        }
        this.status = OrderStatus.REFUNDED;
    }

    //배송시작시
    public void startShipping() {

        if (this.status != OrderStatus.PAID) {
            throw new IllegalStateException("배송을 시작할 수 없는 주문입니다.");
        }

        this.status = OrderStatus.SHIPPING;
    }

    //배송완료시
    public void complete() {

        if (this.status != OrderStatus.SHIPPING) {
            throw new IllegalStateException("배송 완료 처리할 수 없는 주문입니다.");
        }

        this.status = OrderStatus.COMPLETED;
    }
}
