package com.project1.programmers.demo.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product getProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    public Page<Product> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));

        int itemsPerPage = 10;
        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.by(sorts));
        return productRepository.findAll(pageable);
    }

    public void delete(long productId) {
        productRepository.deleteById(productId);
    }

    @Transactional
    public void decreaseStock(long productId, int stockQuantity) {
        Product product = productRepository.findByIdWithLock(productId).orElseThrow(
                () -> new EntityNotFoundException("Product not found")
        );

        if (product.getStockQuantity() < stockQuantity) {
            throw new IllegalStateException("Lack of stock");
        }
        product.decreaseStock(stockQuantity);
        // 더티체킹으로 productRepository.save(product) 생략
    }

    public void save(ProductForm productForm) {
        productRepository.save(Product.builder()
                .name(productForm.getName())
                .price(productForm.getPrice())
                .stockQuantity(productForm.getStockQuantity())
                .origin(productForm.getOrigin())
                .imageUrl(productForm.getImageUrl())
                .description(productForm.getDescription())
                .build()
        );
    }

    public void update(Product product, ProductForm productForm) {
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setStockQuantity(productForm.getStockQuantity());
        product.setImageUrl(productForm.getImageUrl());
        product.setOrigin(productForm.getOrigin());
        product.setDescription(productForm.getDescription());
        productRepository.save(product);
    }

    public void createProduct(ProductForm productForm) {
        productRepository.save(Product.builder()
                .name(productForm.getName())
                .price(productForm.getPrice())
                .stockQuantity(productForm.getStockQuantity())
                .origin(productForm.getOrigin())
                .imageUrl(productForm.getImageUrl())
                .description(productForm.getDescription())
                .build());
    }
}
