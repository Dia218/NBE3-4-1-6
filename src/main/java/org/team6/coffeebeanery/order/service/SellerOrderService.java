package org.team6.coffeebeanery.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.team6.coffeebeanery.order.dto.OrderDto;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.repository.OrderRepository;

@RequiredArgsConstructor
@Service
public class SellerOrderService {
    private final OrderRepository orderRepository;

    public Page<OrderDto> getOrders(int page, int size) {
        // 주문 일자 최신순으로 정렬
        Sort sort = Sort.by(Sort.Order.desc("orderCreatedAt"));
        Pageable pageable = PageRequest.of(page, size, sort);
        return orderRepository
                .findAll(pageable)
                .map(this::convertToOrderDto);
    }

    public Page<OrderDto> getOrdersByEmail(String email, int page, int size) {
        Sort sort = Sort.by(Sort.Order.desc("orderCreatedAt"));
        Pageable pageable = PageRequest.of(page, size, sort);
        return orderRepository
                .findAllByCustomerEmail(email, pageable)
                .map(this::convertToOrderDto);
    }

    // Order -> OrderResponseDto 변환 메서드
    private OrderDto convertToOrderDto(Order order) {
        return OrderDto
                .builder()
                .orderId(order.getOrderId())
                .customerEmail(order.getCustomerEmail())
                .address(order.getAddress())
                .orderCreatedAt(order.getOrderCreatedAt())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .orderDetails(order.getOrderDetails())
                .build();
    }

    public long count() {
        return orderRepository.count();
    }
}
