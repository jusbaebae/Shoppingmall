package com.example.shoppingmall.product.repository;

import com.example.shoppingmall.product.entity.Product;
import com.example.shoppingmall.product.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusNot(ProductStatus status);
}
