package org.team6.coffeebeanery.order.service;

import lombok.RequiredArgsConstructor;
import org.team6.coffeebeanery.common.constant.OrderStatus;
import org.team6.coffeebeanery.common.model.Address;
import org.team6.coffeebeanery.order.dto.OrderDTO;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.model.OrderDetail;
import org.team6.coffeebeanery.order.repository.OrderDetailRepository;
import org.team6.coffeebeanery.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.team6.coffeebeanery.product.dto.ProductDTO;
import org.team6.coffeebeanery.product.model.Product;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyerOrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    // DB에 존재하는 이메일인지 검증
    public boolean validateEmail(String email) {
        List<Order> result = orderRepository.findAllByCustomerEmail(email);
        return !result.isEmpty();
    }

    // 이메일에 해당하는 주문 목록
    public Page<OrderDTO> getListByEmail(String email, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("orderCreatedAt"));

        int itemsPerPage = 5;
        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.by(sorts));
        return orderRepository.findAllByCustomerEmail(email, pageable).map(OrderDTO::toDTO);
    }

    // Order 저장
    public Order saveOrder(String email, Address address, long totalPrice) {
        Order order = Order.builder()
                .customerEmail(email)
                .address(address)
                .totalPrice(totalPrice)
                .orderStatus(OrderStatus.ORDERED)
                .build();
        orderRepository.save(order);
        return order;
    }

    // OrderDetail 저장
    public OrderDetail saveOrderDetail(ProductDTO item, Order order, Product product) {
        return orderDetailRepository.save(OrderDetail.builder()
                .order(order)
                .product(product)
                .orderPrice(item.getProductPrice())
                .productQuantity(item.getProductStock())
                .build());
    }

    public long count() {
        return orderRepository.count();
    }
}
