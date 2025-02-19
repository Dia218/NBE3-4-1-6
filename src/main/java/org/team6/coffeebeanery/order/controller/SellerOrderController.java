package org.team6.coffeebeanery.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.team6.coffeebeanery.common.dto.PageDTO;
import org.team6.coffeebeanery.order.dto.OrderDTO;
import org.team6.coffeebeanery.order.service.SellerOrderService;

@RequiredArgsConstructor
@RestController
public class SellerOrderController {
    
    private final SellerOrderService sellerOrderService;
    
    @GetMapping("/admin/orders")
    public PageDTO<OrderDTO> getOrders(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size,
                                       @RequestParam(required = false) String email) {
        Page<OrderDTO> orderPage;
        if (email != null) {
            orderPage = sellerOrderService.getOrdersByEmail(email, page, size);
        }
        else {
            orderPage = sellerOrderService.getOrders(page, size);
        }
        return new PageDTO<>(orderPage);
    }
}

