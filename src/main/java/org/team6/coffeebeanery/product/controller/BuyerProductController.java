package org.team6.coffeebeanery.product.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.team6.coffeebeanery.product.dto.ProductDTO;
import org.team6.coffeebeanery.product.service.BuyerProductService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BuyerProductController {
    private final BuyerProductService productService;
    
    // 모든 상품 목록을 가져오는 API
    @GetMapping("/list")
    public List<ProductDTO> getAllProducts(HttpSession session) {
        List<ProductDTO> productList = productService.getAllProducts();
        List<ProductDTO> cart = productService.getCart(session);
        session.setAttribute("cart", cart); // 세션에 장바구니 정보 저장
        return productList; // 상품 목록만 반환
    }
    
    // 장바구니의 상세 정보를 반환하는 API
    @GetMapping("/cart")
    public List<ProductDTO> getCartDetails(HttpSession session) {
        List<ProductDTO> cart = productService.getCart(session);
        if (cart == null) {
            cart = new ArrayList<>(); // 장바구니가 비어있을 경우 빈 리스트로 초기화
        }
        return cart; // 장바구니 정보 반환
    }
    
    // 장바구니에 상품을 추가하는 API
    @PostMapping("/add-to-cart")
    @ResponseStatus(HttpStatus.CREATED) // 상태 코드 201로 설정
    public void addToCart(@RequestParam("id") Long productId, @RequestParam("quantity") int quantity,
                          HttpSession session) {
        productService.saveCart(productId, quantity, session);
    }
    
    // 장바구니에서 상품을 제거하는 API
    @DeleteMapping("/remove-from-cart")
    @ResponseStatus(HttpStatus.OK) // 상태 코드 200으로 설정
    public void removeFromCart(@RequestParam("id") Long productId, HttpSession session) {
        List<ProductDTO> cart = productService.getCart(session);
        if (cart != null) {
            cart.removeIf(item -> item.getProductId()
                                      .equals(productId)); // 지정된 상품 제거
        }
    }
    
    // 장바구니의 상품 수량을 업데이트하는 API
    @PatchMapping("/update-cart")
    @ResponseStatus(HttpStatus.OK) // 상태 코드 200으로 설정
    public void updateCart(@RequestParam("id") Long productId, @RequestParam("quantity") int quantity,
                           HttpSession session) {
        List<ProductDTO> cart = productService.getCart(session);
        if (cart != null) {
            for (ProductDTO item : cart) {
                if (item.getProductId()
                        .equals(productId)) {
                    item.setProductStock(quantity); // 수량 업데이트
                    break;
                }
            }
        }
    }
    
    // 장바구니 총 금액 계산 메서드
    @GetMapping("/total-price")
    public long calculateTotalPrice(List<ProductDTO> cart) {
        return (cart != null) ? cart.stream()
                                    .mapToLong(item -> item.getProductPrice() * item.getProductStock())
                                    .sum() : 0; // 장바구니 상품 가격 합산
    }
}