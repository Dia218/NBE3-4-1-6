package com.project1.programmers.demo.order;

import com.project1.programmers.demo.cart.CartItem;
import com.project1.programmers.demo.delivery.Address;
import com.project1.programmers.demo.product.Product;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public boolean validateEmail(String email) {
        List<Order> result = orderRepository.findAllByEmail(email);
        return !result.isEmpty();
    }

    public Page<Order> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));

        int itemsPerPage = 5;
        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.by(sorts));
        return orderRepository.findAll(pageable);
    }

    public Page<Order> getListByEmail(String email, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));

        int itemsPerPage = 5;
        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.by(sorts));
        return orderRepository.findAllByEmail(email, pageable);
    }

    public Order saveOrder(String email, Address address, long totalPrice) {
        Order order = Order.builder()
                .email(email)
                .address(address)
                .totalPrice(totalPrice)
                .isBatchProcessed(false)
                .build();
        orderRepository.save(order);
        return order;
    }

    public void saveOrderItem(CartItem item, Order order, Product product) {
        orderItemRepository.save(OrderItem.builder()
                        .order(order)
                        .product(product)
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .build());
    }

    public Order getOrderById(long id) {
       return orderRepository.findById(id).orElseThrow(
               () -> new EntityNotFoundException("Order not found")
       );
    }
}
