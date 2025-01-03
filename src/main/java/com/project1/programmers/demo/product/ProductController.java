package com.project1.programmers.demo.product;

import com.project1.programmers.demo.cart.CartItem;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    public String productList(Model model,
                              @RequestParam(value="page", defaultValue = "0") int page) {

        Page<Product> paging = productService.getList(page);
        model.addAttribute("paging", paging);
        return "product_list";
    }

    @PostMapping("")
    public String addToCart(@RequestParam("id") Long id,
                            @RequestParam("quantity") int quantity,
                            HttpSession session) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
           cart = new ArrayList<>();
           session.setAttribute("cart", cart);
        }

        for(CartItem item : cart) {
            if(item.getProductId().equals(id)) {
                item.setQuantity(quantity);
                return "redirect:/products";
            }
        }

        cart.add(CartItem.builder()
                .productId(id)
                .quantity(quantity)
                .build());

        return "redirect:/products";
    }
}
