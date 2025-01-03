package org.dia.coffeebeanery.product.controller;

import lombok.RequiredArgsConstructor;
import org.dia.coffeebeanery.product.model.Product;
import org.dia.coffeebeanery.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ProductController {
    
    private final ProductService productService;
    
    @GetMapping({"/product/list", "/admin/products"}) //구매자용) 상품 목록 페이지, 판매자용 상품 목록 페이지
    public Page<Product> getProductList(@RequestParam(value = "page", defaultValue = "0") int page) {
        return this.productService.getAllProductList(page);
    }
    
    @GetMapping("/product/list/{productId}") //구매자용) 상품 장바구니 담기 시 수량 확인
    public ResponseEntity<String> checkProductAvailability(@PathVariable Integer productId,
                                                           @RequestParam Integer requestedQuantity) {
        
        int stock = productService.getProductStock(productId);
        if (stock >= requestedQuantity) {
            return ResponseEntity.ok("Stock available: " + stock);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body("Insufficient stock. Available: " + stock + ", Requested: " + requestedQuantity);
    }
    
    @PostMapping("/admin/products") //판매자용) 새 상품 생성
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }
    
    @PutMapping("/admin/products/{productId}") //판매자용) 기존 상품 정보 수정
    public Product updateProduct(@PathVariable Integer productId, @RequestBody Product updatedProduct) {
        return productService.updateProduct(productId, updatedProduct);
    }
    
    @DeleteMapping("/admin/products/{productId}") //판매자용) 상품 삭제
    public void deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
    }
    
}
