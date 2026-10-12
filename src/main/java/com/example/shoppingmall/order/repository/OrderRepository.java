package com.example.shoppingmall.order.repository;

import com.example.shoppingmall.member.entity.Member;
import com.example.shoppingmall.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdAndMember(Long orderId, Member member);

    List<Order> findAllByMemberId(Long memberId);
}
