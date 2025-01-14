package org.team6.coffeebeanery.order.controller;

import lombok.RequiredArgsConstructor;
import org.team6.coffeebeanery.order.service.BuyerOrderService;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BuyerOrderController {
    private final BuyerOrderService buyerOrderService;

}