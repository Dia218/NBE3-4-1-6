package org.team6.coffeebeanery.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.team6.coffeebeanery.order.dto.OrderDTO;
import org.team6.coffeebeanery.order.repository.OrderRepository;

@RequiredArgsConstructor
@Service
public class SellerOrderService {
    private final OrderRepository orderRepository;

    public Page<OrderDTO> getOrders(int page, int size) {
        // 주문 일자 최신순으로 정렬
        Sort sort = Sort.by(Sort.Order.desc("orderCreatedAt"));
        Pageable pageable = PageRequest.of(page, size, sort);
        return orderRepository
                .findAll(pageable)
                .map(OrderDTO::toDTO); // mapper 사용 안하고 교원님거 static 메서드로 변경
    }

    public Page<OrderDTO> getOrdersByEmail(String email, int page, int size) {
        Sort sort = Sort.by(Sort.Order.desc("orderCreatedAt"));
        Pageable pageable = PageRequest.of(page, size, sort);
        return orderRepository
                .findAllByCustomerEmail(email, pageable)
                .map(OrderDTO::toDTO);
    }
}