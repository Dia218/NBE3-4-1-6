package org.team6.coffeebeanery.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.team6.coffeebeanery.order.service.SellerOrderService;

@RequiredArgsConstructor
@RestController
public class SellerOrderController {
    private final SellerOrderService sellerOrderService;

}
