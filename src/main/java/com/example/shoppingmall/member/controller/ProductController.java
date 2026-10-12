package com.example.shoppingmall.member.controller;

import com.example.shoppingmall.member.dto.ProductRequest;
import com.example.shoppingmall.member.dto.ProductResponse;
import com.example.shoppingmall.member.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    //상품등록
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestPart("product") ProductRequest request, @RequestPart("image") MultipartFile image){

        ProductResponse productResponse =  productService.createProduct(request, image);

        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    //상품수정
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @Valid @RequestPart("product") ProductRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ){
        System.out.println("받은 정상가격: " + request.getPrice());
        System.out.println("받은 할인가격: " + request.getSalePrice());

        return productService.updateProduct(id,request,image);
    }

    //상품삭제
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    //상품전체조회
    @GetMapping
    public List<ProductResponse> getProducts(){
        return productService.getProducts();
    }

    //상품상세조회
    @GetMapping("/{id}")
    public ProductResponse getProductId(@PathVariable Long id){
        return productService.getProduct(id);
    }
}
