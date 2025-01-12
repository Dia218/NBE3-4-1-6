package org.team6.coffeebeanery.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.team6.coffeebeanery.order.dto.OrderDetailResponseDto;
import org.team6.coffeebeanery.order.dto.OrderResponseDto;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.model.OrderDetail;
import org.team6.coffeebeanery.order.repository.OrderRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SellerOrderService {
    private final OrderRepository orderRepository;

    public Page<OrderResponseDto> getOrders(int page) {
        // 주문 일자 최신순으로 정렬
        Sort sort = Sort.by(Sort.Order.desc("orderCreatedAt"));
        Pageable pageable = PageRequest.of(page, 10, sort);
        return orderRepository
                .findAll(pageable)
                .map(this::convertToOrderResponseDto);
    }

    public List<Order> getOrdersByEmail(String email) {
        Sort sort = Sort.by(Sort.Order.desc("orderCreatedAt"));
        return orderRepository.findAllByCustomerEmail(email, sort);
    }

    // Order -> OrderResponseDto 변환 메서드
    private OrderResponseDto convertToOrderResponseDto(Order order) {
        return OrderResponseDto
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

}
