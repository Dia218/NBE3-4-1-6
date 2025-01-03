package com.mysite.coffee.admin;

import com.mysite.coffee.product.Product;
import com.mysite.coffee.product.ProductRequestDto;
import com.mysite.coffee.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;

    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin_products";
    }

    @PostMapping("/products")
    public String createProduct(@ModelAttribute ProductRequestDto productRequestDto) {
        productService.create(productRequestDto);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/{id}")
    public String getEditProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "admin_product_edit";
    }

    @PutMapping("/products/{id}")
    public String modifyProduct(@ModelAttribute ProductRequestDto productRequestDto,
                                @PathVariable("id") Long id) {
        productService.modify(id,productRequestDto);
        return "redirect:/admin/products";
    }
}
