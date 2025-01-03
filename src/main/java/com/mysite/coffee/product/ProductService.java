package com.mysite.coffee.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void create(ProductRequestDto productRequestDto) {
        Product product = new Product(productRequestDto);
        productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void modify(Long id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        product.setStock(productRequestDto.getStock());
        product.setDescription(productRequestDto.getDescription());
        product.setImage(productRequestDto.getImage());
        productRepository.save(product);
    }
}
