package org.team6.coffeebeanery.product.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.team6.coffeebeanery.product.dto.ProductDTO;
import org.team6.coffeebeanery.product.service.BuyerProductService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BuyerProductController {

    private final BuyerProductService productService;

    // 세션에서 장바구니를 가져오는 메서드
    @SuppressWarnings("unchecked")
    private List<ProductDTO> getCart(HttpSession session) {
        return (List<ProductDTO>) session.getAttribute("cart");
    }

    // 상품 목록 페이지(홈)
    @GetMapping("/")
    public String productList(Model model, HttpSession session) {
        List<ProductDTO> productList = productService.getAllProducts();
        List<ProductDTO> cart = getCart(session);

        long totalPrice = calculateTotalPrice(cart);

        model.addAttribute("products", productList);
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);

        return "product_list";
    }

    // 장바구니 페이지
    @GetMapping("/cart")
    public String cartPage(Model model, HttpSession session) {
        List<ProductDTO> cart = getCart(session);

        long totalPrice = calculateTotalPrice(cart);

        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);

        return "cart";
    }

    // 장바구니에 상품 추가
    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("id") Long productId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session) {
        List<ProductDTO> cart = getCart(session);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        // 장바구니에 상품 중복 확인 및 수량 업데이트
        boolean itemExists = false;
        for (ProductDTO item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setProductStock(item.getProductStock() + quantity);
                itemExists = true;
                break;
            }
        }

        // 같은 상품 있는지 확인 후 없으면 새상품 추가
        if (!itemExists) {
            ProductDTO productDTO = productService.getProduct(productId);
            productDTO.setProductStock(quantity);
            cart.add(productDTO);
        }

        return "redirect:/cart";
    }

    // 장바구니에서 상품 삭제
    @PostMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("id") Long productId, HttpSession session) {
        List<ProductDTO> cart = getCart(session);
        if (cart != null) {
            cart.removeIf(item -> item.getProductId().equals(productId));
        }
        return "redirect:/cart";
    }

    // 장바구니에서 수량 변경
    @PostMapping("/updateCart")
    public String updateCart(@RequestParam("id") Long productId,
                             @RequestParam("quantity") int quantity,
                             HttpSession session) {
        List<ProductDTO> cart = getCart(session);
        if (cart != null) {
            for (ProductDTO item : cart) {
                if (item.getProductId().equals(productId)) {
                    item.setProductStock(quantity);
                    break;
                }
            }
        }
        return "redirect:/cart";
    }

    // 장바구니 총 금액 계산
    private long calculateTotalPrice(List<ProductDTO> cart) {
        return (cart != null) ? cart.stream()
                .mapToLong(item -> item.getProductPrice() * item.getProductStock())
                .sum() : 0;
    }
}
