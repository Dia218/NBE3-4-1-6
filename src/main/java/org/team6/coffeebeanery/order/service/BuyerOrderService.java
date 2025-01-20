package org.team6.coffeebeanery.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.team6.coffeebeanery.common.constant.OrderStatus;
import org.team6.coffeebeanery.common.exception.InvalidInputException;
import org.team6.coffeebeanery.common.exception.ResourceNotFoundException;
import org.team6.coffeebeanery.common.model.Address;
import org.team6.coffeebeanery.delivery.model.Delivery;
import org.team6.coffeebeanery.order.converter.OrderConverter;
import org.team6.coffeebeanery.order.dto.OrderCreateReqBody;
import org.team6.coffeebeanery.order.dto.OrderDTO;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.repository.OrderDetailRepository;
import org.team6.coffeebeanery.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.team6.coffeebeanery.product.dto.ProductDTO;
import org.team6.coffeebeanery.product.model.Product;
import org.team6.coffeebeanery.product.service.SellerProductService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyerOrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final SellerProductService sellerProductService;

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
        Order order = OrderConverter.toOrder(email, address, totalPrice);
        orderRepository.save(order);
        return order;
    }

    // OrderDetail 저장
    public void saveOrderDetail(ProductDTO item, Order order, Product product) {
        orderDetailRepository.save(OrderConverter.toOrderDetail(item, order, product));
    }

    public long count() {
        return orderRepository.count();
    }

    @Transactional
    public void saveOrderWithCart(List<ProductDTO> cart, OrderCreateReqBody reqBody) {
        String email = reqBody.email();
        Address address = reqBody.toAddress();

        Order order = saveOrder(email, address, reqBody.totalPrice());

        for (ProductDTO item : cart) {
            Product product = sellerProductService.getProductById(item.getProductId());
            saveOrderDetail(item, order, product);
            sellerProductService.decreaseStock(item.getProductId(), item.getProductStock());
        }
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "order not found - id: " + orderId));

        if(order.getOrderStatus().equals(OrderStatus.ORDERED)) {
            // 주문에 배송 정보가 있다면 연관관계 제거
            if (order.getDelivery() != null) {
                Delivery delivery = order.getDelivery();
                delivery.setOrder(null);
                order.setDelivery(null);
            }
            order.setOrderStatus(OrderStatus.CANCELLED);
        } else {
            throw new InvalidInputException("주문 취소는 배송 전에만 가능합니다.");
        }
    }
}
