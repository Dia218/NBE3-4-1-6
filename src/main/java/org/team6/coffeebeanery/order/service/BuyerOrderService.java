package org.team6.coffeebeanery.order.service;

import lombok.RequiredArgsConstructor;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BuyerOrderService {
    private final OrderRepository orderRepository;


    // Order 객체 저장 메서드
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    // 이메일로 주문 리스트 검색
    public List<Order> findOrdersByEmail(String email) {
        return orderRepository.findByCustomerEmail(email);
    }

    // 이메일 존재 여부 확인 메서드
    public boolean isCustomerEmailExists(String email) {
        return orderRepository.existsByCustomerEmail(email); // Repository 호출
    }
}
