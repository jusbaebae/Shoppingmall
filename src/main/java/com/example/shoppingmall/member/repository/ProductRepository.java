package com.example.shoppingmall.member.repository;

import com.example.shoppingmall.member.entity.Product;
import com.example.shoppingmall.member.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusNot(ProductStatus status);
}
