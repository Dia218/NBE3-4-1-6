package org.team6.coffeebeanery.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.team6.coffeebeanery.common.exception.InvalidInputException;
import org.team6.coffeebeanery.common.exception.ResourceNotFoundException;
import org.team6.coffeebeanery.product.model.Product;
import org.team6.coffeebeanery.product.repository.ProductRepository;

@RequiredArgsConstructor
@Service
public class SellerProductService {
    private final ProductRepository productRepository;
    
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                                .orElseThrow(
                                        () -> new ResourceNotFoundException("Product not found - id: " + productId));
    }
    
    public void decreaseStock(Long productId, int reduceQuantity) {
        Product existingProduct = productRepository.findById(productId)
                                                   .orElseThrow(() -> new ResourceNotFoundException(
                                                           "Product not found - id: " + productId));
        
        int currentStock = existingProduct.getProductStock();
        if (reduceQuantity > currentStock) {
            throw new InvalidInputException("Cannot decrease stock. Current stock (" + currentStock +
                                            ") is less than the requested quantity (" + reduceQuantity + ").");
        }
        
        existingProduct.setProductStock(currentStock - reduceQuantity);
        productRepository.save(existingProduct);
    }
    
    public Page<Product> getAllProductList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.productRepository.findAll(pageable);
    }
    
    public void createProduct(Product product) {
        this.productRepository.save(product);
    }
    
    public void updateProduct(Long productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId)
                                                   .orElseThrow(() -> new ResourceNotFoundException(
                                                           "Product not found - id: " + productId));
        
        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setProductDescription(updatedProduct.getProductDescription());
        existingProduct.setProductPrice(updatedProduct.getProductPrice());
        existingProduct.setProductImageURL(updatedProduct.getProductImageURL());
        existingProduct.setProductStock(updatedProduct.getProductStock());
        
        productRepository.save(existingProduct);
    }
    
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new ResourceNotFoundException(
                                                   "Product not found - id: " + productId));
        productRepository.delete(product);
    }
}
