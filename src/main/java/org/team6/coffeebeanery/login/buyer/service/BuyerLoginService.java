package org.team6.coffeebeanery.login.buyer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team6.coffeebeanery.order.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class BuyerLoginService {
    private final OrderRepository orderRepository;

    public boolean isCustomerEmailExists(String email) {
        return orderRepository.existsByCustomerEmail(email);
    }
}
