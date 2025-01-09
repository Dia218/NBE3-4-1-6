package org.team6.coffeebeanery.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.service.SellerOrderService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SellerOrderController {

    private final SellerOrderService sellerOrderService;

    @GetMapping("/admin/orders")
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = sellerOrderService.getOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/admin/orders/search")
    public ResponseEntity<List<Order>> getOrdersByEmail(@RequestParam("email") String email) {
        List<Order> orders = sellerOrderService.getOrdersByEmail(email);
        return ResponseEntity.ok(orders);
    }
}
