package org.team6.coffeebeanery.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team6.coffeebeanery.delivery.model.Delivery;
import org.team6.coffeebeanery.delivery.service.DeliveryService;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;


    // 배송 정보 페이지
    @GetMapping("/{deliveryId}")
    public String deliveryInfoPage(@PathVariable Long deliveryId, Model model) {
        Delivery delivery = deliveryService.getDeliveryById(deliveryId); // 서비스 계층을 통해 배송 정보 조회

        model.addAttribute("delivery", delivery);
        model.addAttribute("deliveryStatus", delivery.getOrder().getOrderStatus().getStatus());
        return "delivery"; // 배송 정보 HTML 반환
    }
}