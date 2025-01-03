package com.project1.programmers.demo.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/email")
    public String inputEmail(EmailForm emailForm){
        return "email_validation";
    }

    @PostMapping("/email")
    public String validateEmail(@Valid EmailForm emailForm,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        String email = emailForm.getEmail();

        if(bindingResult.hasErrors()){
            return "email_validation";
        }

        if(!orderService.validateEmail(email)){
            bindingResult.reject("invalidEmail", "주문 내역이 존재하지 않는 이메일입니다.");
            return "email_validation";
        }

        redirectAttributes.addFlashAttribute("email", email);

        return "redirect:/order/list";
    }

    @GetMapping("/list")
    public String orderList(@ModelAttribute("email") String email,
                            @RequestParam(value = "page", defaultValue = "0") int page,
                            Model model) {

        Page<Order> paging = orderService.getListByEmail(email, page);
        model.addAttribute("paging", paging);
        return "order_list";
    }

    // 배송정보에서 뒤로가기 버튼 누르면 실행
    @PostMapping("/list")
    public String getOrderList(@RequestParam("email") String email,
                            @RequestParam(value = "page", defaultValue = "0") int page,
                            Model model) {

        Page<Order> paging = orderService.getListByEmail(email, page);
        model.addAttribute("paging", paging);
        return "order_list";
    }

}
