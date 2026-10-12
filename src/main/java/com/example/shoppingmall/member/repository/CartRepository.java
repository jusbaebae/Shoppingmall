package com.example.shoppingmall.member.repository;

import com.example.shoppingmall.member.entity.Cart;
import com.example.shoppingmall.member.entity.Member;
import com.example.shoppingmall.member.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByMemberAndProduct(Member member, Product product);

    List<Cart> findAllByMemberId(Long memberId);

    void deleteAllByMemberId(Long memberId);
}
