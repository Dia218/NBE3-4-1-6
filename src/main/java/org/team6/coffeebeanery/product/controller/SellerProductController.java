package org.team6.coffeebeanery.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.team6.coffeebeanery.product.dto.ProductDTO;
import org.team6.coffeebeanery.product.mapper.ProductMapper;
import org.team6.coffeebeanery.product.model.Product;
import org.team6.coffeebeanery.product.service.SellerProductService;

@RequiredArgsConstructor
@RestController
public class SellerProductController {
    private final SellerProductService sellerProductService;
    private final ProductMapper productMapper;
    
    @GetMapping({"/seller/products"}) //판매자용) 상품 목록 페이지
    public Page<ProductDTO> getAllProductList(@RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Product> products = sellerProductService.getAllProductList(page);
        return products.map(productMapper::toDTO);
    }
    
    @PostMapping("/seller/products") //판매자용) 새 상품 생성
    public void createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        sellerProductService.createProduct(product);
    }
    
    @PutMapping("/seller/products/{productId}") //판매자용) 기존 상품 정보 수정
    public void updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductDTO productDTO) {
        sellerProductService.updateProduct(productId, productMapper.toEntity(productDTO));
    }
    
    @DeleteMapping("/seller/products/{productId}") //판매자용) 상품 삭제
    public void deleteProduct(@PathVariable Long productId) {
        sellerProductService.deleteProduct(productId);
    }
}
