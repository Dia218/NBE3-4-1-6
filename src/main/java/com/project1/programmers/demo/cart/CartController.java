package com.project1.programmers.demo.cart;

import com.project1.programmers.demo.delivery.Address;
import com.project1.programmers.demo.order.EmailForm;
import com.project1.programmers.demo.order.Order;
import com.project1.programmers.demo.order.OrderForm;
import com.project1.programmers.demo.order.OrderService;
import com.project1.programmers.demo.product.Product;
import com.project1.programmers.demo.product.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {

    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping("")
    public String cart(HttpSession session,
                       Model model,
                       @RequestParam(value="page", defaultValue="0") int page,
                       OrderForm orderForm) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if(cart == null) {
            return "cart";
        }

        long totalPrice = 0;
        for(CartItem item : cart) {
            Product product = productService.getProduct(item.getProductId());
            item.setProductName(product.getName());
            item.setPrice(product.getPrice());
            item.setImageUrl(product.getImageUrl());
            totalPrice += product.getPrice() * item.getQuantity();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);

        // Page<CartItem> paging = CommonUtil.convertListToPage(cart, page);
        // model.addAttribute("paging", paging);

        return "cart";
    }

    @Transactional
    @PostMapping("")
    public String order(EmailForm emailForm,
                        @Valid OrderForm orderForm,
                        BindingResult bindingResult,
                        @RequestParam("totalPrice") long totalPrice,
                        HttpSession session,
                        Model model) {

        List<CartItem> cart = (List<CartItem>)session.getAttribute("cart");

        if(cart == null) {
            return "cart";
        }

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);
        if(bindingResult.hasErrors()) {
            return "cart";
        }

        String email = orderForm.getEmail();
        Address address = new Address(orderForm.getBaseAddress(), orderForm.getDetailAddress(), orderForm.getZipCode());

        Order order = orderService.saveOrder(email, address, totalPrice);
        try {
            for (CartItem item : cart) {
                Product product = productService.getProduct(item.getProductId());
                orderService.saveOrderItem(item, order, product);
                productService.decreaseStock(item.getProductId(), item.getQuantity());
            }
        } catch(IllegalStateException e) {
            bindingResult.reject("LackOfStock", "주문 가능한 재고를 초과했습니다.");
            return "cart";
        }

        return "redirect:/order/email";
    }

    @PostMapping("/update")
    public String updateCartItem(OrderForm orderForm,
                                @RequestParam("id") Long id,
                                @RequestParam("quantity") int quantity,
                                HttpSession session) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if(cart == null)
            return "cart.html";

        for(CartItem item : cart) {
            if(item.getProductId().equals(id)) {
                item.setQuantity(quantity);
            }
        }
        return "redirect:/cart";
    }

    @GetMapping("/delete/{id}")
    public String deleteCartItem(OrderForm orderForm,
                                 HttpSession session,
                                 @PathVariable("id") Long productId) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if(cart == null)
            return "cart.html";

        cart.removeIf(item -> item.getProductId().equals(productId));
        return "redirect:/cart";
    }
}
