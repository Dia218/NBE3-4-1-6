package org.team6.coffeebeanery.login.seller.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.team6.coffeebeanery.login.seller.model.PasswordForm;

@Controller
@RequiredArgsConstructor
public class SellerLoginController {
    private static final String ADMIN_PASSWORD = "1111"; // 관리자 비밀번호

    // 관리자 로그인 폼 페이지 매핑
    @GetMapping("/seller/login")
    public String showPasswordForm(Model model) {
        model.addAttribute("passwordForm", new PasswordForm()); // 빈 PasswordForm 객체 전달
        model.addAttribute("error", false); // 초기 에러 메시지 상태
        return "sellerLogin"; // sellerLogin.html 반환
    }

    // 비밀번호 확인 처리
    @PostMapping("/seller/login")
    public String checkPassword(
            @Valid @ModelAttribute PasswordForm passwordForm, // 유효성 검사된 PasswordForm 객체
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "sellerLogin"; // 유효성 검사 실패 시 다시 로그인 폼 반환
        }

        if (ADMIN_PASSWORD.equals(passwordForm.getPassword())) {
            // 비밀번호가 일치하면 order/list로 리다이렉트
            return "redirect:/order/list";
        } else {
            // 비밀번호가 틀리면 에러 메시지와 함께 로그인 폼 반환
            model.addAttribute("error", true);
            return "sellerLogin";
        }
    }
}
