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
        List<ProductDTO> cart = (List<ProductDTO>) session.getAttribute("cart");
        
        // cartObject가 null이면 예외 처리
        if (cart == null) {
            System.out.println("??");
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        return cart;
    }
    
    public void saveCart(Long productId, int quantity, HttpSession session) {
        List<ProductDTO> cart = getCart(session);

        boolean itemExists = false; // 상품 존재 여부를 false로 초기화

        // 카트에서 상품 검색 및 수량 증가
        for (ProductDTO item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setProductStock(item.getProductStock() + quantity); // 기존 상품의 수량 증가
                itemExists = true; // 상품이 이미 존재함을 표시
                break; // 상품을 찾았으므로 루프 종료
            }
        }

        // 카트에 상품이 없을 경우 새로 추가
        if (!itemExists) {
            ProductDTO productDTO = getProduct(productId); // 상품 정보 가져오기
            productDTO.setProductStock(quantity); // 요청된 수량 설정
            cart.add(productDTO); // 카트에 추가
        }

        System.out.println("Session ID: " + session.getId());
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

