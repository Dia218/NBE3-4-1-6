package org.team6.coffeebeanery.order.service;

import lombok.RequiredArgsConstructor;
import org.team6.coffeebeanery.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BuyerOrderService {
    private final OrderRepository orderRepository;

}
