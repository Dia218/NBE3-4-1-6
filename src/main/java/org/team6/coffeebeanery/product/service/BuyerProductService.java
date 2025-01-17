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

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team6.coffeebeanery.common.exception.ResourceNotFoundException;
import org.team6.coffeebeanery.product.dto.ProductDTO;
import org.team6.coffeebeanery.product.mapper.ProductMapper;
import org.team6.coffeebeanery.product.model.Product;
import org.team6.coffeebeanery.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuyerProductService {
    
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    
    // 모든 상품 조회 후 DTO로 변환
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                                .stream()
                                .map(productMapper::toDTO)
                                .collect(Collectors.toList());
    }
    
    // 특정 상품 조회 후 DTO로 변환
    public ProductDTO getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new IllegalArgumentException(
                                                   "상품을 찾을 수 없습니다. ID: " + productId));
        return productMapper.toDTO(product);
    }
    
    // 상품 재고 감소
    @Transactional
    public void decreaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new IllegalArgumentException(
                                                   "상품을 찾을 수 없습니다. ID: " + productId));
        if (product.getProductStock() < quantity) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
        product.setProductStock(product.getProductStock() - quantity);
    }
    
    public List<ProductDTO> getCart(HttpSession session) {
        Object cartObject = session.getAttribute("cart");
        
        // cartObject가 null이면 예외 처리
        if (cartObject == null) {
            throw new ResourceNotFoundException("장바구니 목록을 불러오는데 실패했습니다");
        }
        
        // cartObject가 List인지를 확인
        if (!(cartObject instanceof List<?> rawList)) {
            throw new IllegalStateException("세션에 저장된 장바구니 데이터가 List 타입이 아닙니다");
        }
        
        return convertToProductDTO(rawList);
    }
    
    public void saveCart(Long productId, int quantity, HttpSession session) {
        List<ProductDTO> cart = getCart(session);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        
        boolean itemExists = true;
        for (ProductDTO item : cart) {
            if (item.getProductId()
                    .equals(productId)) {
                item.setProductStock(item.getProductStock() + quantity); // 기존 상품의 수량 증가
                continue;
            }
            itemExists = false;
        }
        
        if (!itemExists) {
            ProductDTO productDTO = getProduct(productId);
            productDTO.setProductStock(quantity);
            cart.add(productDTO);
        }
    }
    
    private List<ProductDTO> convertToProductDTO(List<?> rawList) {
        List<ProductDTO> productList = new ArrayList<>();
        
        // rawList 안의 각 항목을 하나씩 ProductDTO로 변환하여 새 리스트에 추가
        for (Object item : rawList) {
            if (item instanceof ProductDTO) {
                productList.add((ProductDTO) item);
            }
            else {
                throw new IllegalStateException("장바구니에 ProductDTO 타입이 아닌 데이터가 포함되어 있습니다");
            }
        }
        return productList;
    }
}

