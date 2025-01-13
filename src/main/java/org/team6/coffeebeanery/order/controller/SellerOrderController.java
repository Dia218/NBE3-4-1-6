package org.team6.coffeebeanery.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.team6.coffeebeanery.order.dto.OrderDto;
import org.team6.coffeebeanery.order.dto.PageDto;
import org.team6.coffeebeanery.order.service.SellerOrderService;

@RequiredArgsConstructor
@RestController
public class SellerOrderController {

    private final SellerOrderService sellerOrderService;

    @GetMapping("/admin/orders")
    public PageDto<OrderDto> getOrders(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(required = false) String email) {
        Page<OrderDto> orderPage;
        if (email != null) {
            orderPage = sellerOrderService.getOrdersByEmail(email, page, size);
        } else {
            orderPage = sellerOrderService.getOrders(page, size);
        }
        return new PageDto<>(orderPage);
    }
}
