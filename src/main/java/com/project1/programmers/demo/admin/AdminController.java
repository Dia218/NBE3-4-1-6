package com.project1.programmers.demo.admin;

import com.project1.programmers.demo.order.Order;
import com.project1.programmers.demo.order.OrderService;
import com.project1.programmers.demo.product.Product;
import com.project1.programmers.demo.product.ProductForm;
import com.project1.programmers.demo.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping("")
    public String getAdmin(PasswordForm passwordForm) {
        return "admin_password";
    }

    @PostMapping("")
    public String validateAdmin(
            @Valid PasswordForm passwordForm,
            BindingResult bindingResult) {
        if(!passwordForm.getPassword().equals("admin")){
            bindingResult.reject("invalidPassword", "잘못된 비밀번호입니다.");
            return "admin_password";
        }
        return "redirect:/admin/orders";
    }

    @GetMapping("/orders")
    public String showOrderList(@RequestParam(value="page", defaultValue = "0") int page,
                                Model model){
        Page<Order> paging = orderService.getList(page);
        model.addAttribute("paging", paging);

        return "admin_order_list";
    }

    @GetMapping("/products")
    public String showProductList(@RequestParam(value="page", defaultValue = "0")int page,
                                  Model model){
        Page<Product> paging = productService.getList(page);
        model.addAttribute("paging", paging);
        return "admin_product_list";
    }


    @GetMapping("/product/create")
    public String createProduct(ProductForm productForm){
        return "product_form";
    }

    @PostMapping("/product/create")
    public String createProduct(@Valid ProductForm productForm,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "product_form";
        }

        productService.save(productForm);
        return "redirect:/admin/products";
    }

    @GetMapping("/product/update/{id}")
    public String updateProduct(@PathVariable("id")Long id,
                                ProductForm productForm){
        Product product = productService.getProduct(id);
        productForm.setName(product.getName());
        productForm.setPrice(product.getPrice());
        productForm.setStockQuantity(product.getStockQuantity());
        productForm.setOrigin(product.getOrigin());
        productForm.setImageUrl(product.getImageUrl());
        productForm.setDescription(product.getDescription());
        return "product_form";
    }

    @PostMapping("/product/update/{id}")
    public String updateProduct(@PathVariable("id")Long id,
                                @Valid ProductForm productForm,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "product_form";
        }
        Product product = productService.getProduct(id);
        productService.update(product, productForm);
        return "redirect:/admin/products";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }

}
