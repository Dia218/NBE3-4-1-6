package org.team6.coffeebeanery.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SellerOrderService {
    private final OrderRepository orderRepository;

    public List<Order> findAll() {
        // 주문 일자 최신순으로 정렬
        Sort sort = Sort.by(Sort.Order.desc("orderCreatedAt"));
        return orderRepository.findAll(sort);
    }
}
