package org.dia.coffeebeanery.product.service;

import lombok.RequiredArgsConstructor;
import org.dia.coffeebeanery.product.model.Product;
import org.dia.coffeebeanery.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    
    public Page<Product> getAllProductList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.productRepository.findAll(pageable);
    }
    
    public int getProductStock(Integer productId) {
        return this.productRepository.findStockByProductId(productId);
    }
    
    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }
    
    public Product updateProduct(Integer productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId)
                                                   .orElseThrow(() -> new IllegalArgumentException(
                                                           "ERROR: " + productId));
        
        existingProduct.setProduct_name(updatedProduct.getProduct_name());
        existingProduct.setProduct_price(updatedProduct.getProduct_price());
        existingProduct.setProduct_description(updatedProduct.getProduct_description());
        existingProduct.setProduct_stock(updatedProduct.getProduct_stock());
        
        return productRepository.save(existingProduct);
    }
    
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }
    
}
