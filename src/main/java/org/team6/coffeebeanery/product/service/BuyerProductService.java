//package org.team6.coffeebeanery.product.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.team6.coffeebeanery.product.model.Product;
//import org.team6.coffeebeanery.product.repository.ProductRepository;
//
//import jakarta.persistence.EntityNotFoundException;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class BuyerProductService {
//
//    private final ProductRepository productRepository;
//
//    /**
//     * 모든 상품 조회
//     *
//     * @return List<Product>
//     */
//    public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }
//
//    /**
//     * 특정 상품 조회
//     *
//     * @param id 상품 ID
//     * @return Product
//     * @throws EntityNotFoundException 상품이 존재하지 않을 경우
//     */
//    public Product getProduct(Long id) {
//        return productRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다. ID: " + id));
//    }
//
//    /**
//     * 상품 재고 감소
//     *
//     * @param productId 상품 ID
//     * @param quantity 감소할 수량
//     * @throws IllegalStateException 재고가 부족한 경우
//     */
//    @Transactional
//    public void decreaseStock(Long productId, int quantity) {
//        Product product = getProduct(productId);
//        if (product.getStock() < quantity) {
//            throw new IllegalStateException("재고가 부족합니다.");
//        }
//        product.decreaseStock(quantity);
//    }
//}

package org.team6.coffeebeanery.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team6.coffeebeanery.product.dto.ProductDTO;
import org.team6.coffeebeanery.product.mapper.ProductMapper;
import org.team6.coffeebeanery.product.model.Product;
import org.team6.coffeebeanery.product.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuyerProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // 모든 상품 조회 후 DTO로 변환
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    // 특정 상품 조회 후 DTO로 변환
    public ProductDTO getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + productId));
        return productMapper.toDTO(product);
    }

    // 상품 재고 감소
    @Transactional
    public void decreaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + productId));
        if (product.getProductStock() < quantity) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
        product.setProductStock(product.getProductStock() - quantity);
    }
}

