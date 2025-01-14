package org.team6.coffeebeanery.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.team6.coffeebeanery.order.model.EmailForm;
import org.team6.coffeebeanery.order.service.BuyerOrderService;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BuyerOrderController {
    private final BuyerOrderService orderService;

    // 이메일 입력 폼 페이지 표시
    @GetMapping("/check")
    public String showEmailForm(Model model) {
        model.addAttribute("emailForm", new EmailForm()); // 빈 EmailForm 객체 생성
        model.addAttribute("error", false); // 에러 초기값 설정
        return "email_form";
    }

    // 이메일 확인 및 처리
    @PostMapping("/check")
    public String checkEmail(
            @Valid @ModelAttribute EmailForm emailForm, // 유효성 검사된 EmailForm 객체
            BindingResult bindingResult, // 유효성 검사 결과
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "email_form"; // 유효성 검사 실패 시 다시 폼 페이지로 반환
        }

        // 이메일 존재 여부 확인
        boolean emailExists = orderService.isCustomerEmailExists(emailForm.getEmail());

        if (emailExists) {
            // 이메일 존재 시 상세 페이지로 리다이렉트
            return "redirect:/order/detail?email=" + emailForm.getEmail();
        } else {
            // 이메일 미존재 시 에러 메시지 표시
            model.addAttribute("error", true);
            return "email_form";
        }
    }
}
