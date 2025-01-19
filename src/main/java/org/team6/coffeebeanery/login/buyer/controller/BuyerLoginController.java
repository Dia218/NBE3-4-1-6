package org.team6.coffeebeanery.login.buyer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team6.coffeebeanery.login.buyer.model.EmailForm;
import org.team6.coffeebeanery.login.buyer.service.BuyerLoginService;

import java.util.Map;

@RestController
@RequestMapping("/api/buyer")
@RequiredArgsConstructor
public class BuyerLoginController {
    private final BuyerLoginService buyerLoginService;

    @PostMapping("/login")
    public ResponseEntity<?> validateEmail(@RequestBody @Valid EmailForm emailForm) {
        String email = emailForm.getEmail();
        boolean emailExists = buyerLoginService.isCustomerEmailExists(email);

        if (emailExists) {
            return ResponseEntity.ok().body(Map.of(
                    "success", true,
                    "message", "Valid email",
                    "email", email
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "success", false,
                            "message", "No orders found for this email"
                    ));
        }
    }
}