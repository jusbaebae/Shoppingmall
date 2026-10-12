package com.example.shoppingmall.member.service;

import com.example.shoppingmall.member.dto.ProductRequest;
import com.example.shoppingmall.member.dto.ProductResponse;
import com.example.shoppingmall.member.entity.Product;
import com.example.shoppingmall.global.exception.product.ProductNotFoundException;
import com.example.shoppingmall.member.entity.ProductStatus;
import com.example.shoppingmall.member.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;

    public ProductResponse createProduct(ProductRequest request, MultipartFile image){

        Integer stock = request.getStock();

        //품절 상품은 재고를 무조건 0으로 설정
        if (request.getStatus() == ProductStatus.SOLD_OUT) {
            stock = 0;
        }

        //재고 검증
        if (request.getStatus() == ProductStatus.ON_SALE && request.getStock() == 0) {
            throw new IllegalArgumentException("판매중인 상품의 재고는 1개 이상이어야 합니다.");
        }

        //세일 가격 검증
        if (request.getSalePrice() != null) {

            if (request.getSalePrice() >= request.getPrice()) {
                throw new IllegalArgumentException("할인 가격은 정상 가격보다 낮아야 합니다.");
            }
        }

        String imageUrl = fileStorageService.saveFile(image);

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .salePrice(request.getSalePrice())
                .stock(stock)
                .description(request.getDescription())
                .status(request.getStatus())
                .imageUrl(imageUrl)
                .build();

        Product savedProduct = productRepository.save(product);

        return new ProductResponse(savedProduct);
    }

    public List<ProductResponse> getProducts(){

        return productRepository.findByStatusNot(ProductStatus.DELETED)
                                .stream()
                                .map(ProductResponse::new)
                                .toList();
    }

    public ProductResponse getProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return new ProductResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request, MultipartFile image){

        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        int stock = request.getStock();

        //품절이면 재고는 무조건 0
        if (request.getStatus() == ProductStatus.SOLD_OUT) {
            stock = 0;
        }

        //판매중인데 재고가 0이면 안 됨
        if (request.getStatus() == ProductStatus.ON_SALE && request.getStock() == 0) {
            throw new IllegalArgumentException("판매중인 상품의 재고는 1개 이상이어야 합니다.");
        }

        //할인 가격 검증
        if (request.getSalePrice() != null) {
            if (request.getSalePrice() >= request.getPrice()) {
                throw new IllegalArgumentException("할인 가격은 정상 가격보다 낮아야 합니다.");
            }
        }

        String imageUrl = product.getImageUrl();

        if(image != null && !image.isEmpty()){
            //기존 이미지 삭제
            fileStorageService.deleteFile(imageUrl);

            //새 이미지 저장
            imageUrl = fileStorageService.saveFile(image);
        }

        product.update(
                request.getName(),
                request.getPrice(),
                request.getSalePrice(),
                stock,
                request.getDescription(),
                imageUrl,
                request.getStatus()
        );

        return new ProductResponse(product);
    }

    @Transactional
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("상품을 찾을 수 없습니다.")
                );

        product.changeStatus(ProductStatus.DELETED);
    }
}
