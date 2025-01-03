package org.dia.coffeebeanery.order.service;

import lombok.RequiredArgsConstructor;
import org.dia.coffeebeanery.delivery.model.Delivery;
import org.dia.coffeebeanery.delivery.repository.DeliveryRepository;
import org.dia.coffeebeanery.exception.ResourceNotFoundException;
import org.dia.coffeebeanery.order.constant.OrderState;
import org.dia.coffeebeanery.order.model.Order;
import org.dia.coffeebeanery.order.model.OrderDetail;
import org.dia.coffeebeanery.order.repository.OrderRepository;
import org.dia.coffeebeanery.product.model.Product;
import org.dia.coffeebeanery.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final DeliveryRepository deliveryRepository;
    
    public Page<Order> getOrderListByEmail(String email, int page) {
        Pageable pageable = PageRequest.of(page, 10);  // 페이징 처리, 한 페이지에 10개의 주문
        return orderRepository.findByCustomer_email(email, pageable);
    }
    
    public Order createOrder(Order order) { //리팩토링 필요
        checkOrderDetailStock(order);
        setDelivery(order);
        
        // 주문 및 주문 상세 저장
        order.setOrder_state(OrderState.ORDERED);  // 기본 주문 상태 '주문 완료'
        order.setOrder_created_at(LocalDateTime.now()); // 주문 생성 시간 설정
        order.setTotal_price(calculateTotalPrice(order)); // 총 주문 금액 계산
        
        order = orderRepository.save(order);
        setOrderDetails(order);
        return order;
    }
    
    private void checkOrderDetailStock(Order order) {
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            Product product = orderDetail.getProduct();
            
            if (product.getProduct_stock() < orderDetail.getProduct_quantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getProduct_name());
            }
        }
    }
    
    private void setDelivery(Order order) {
        Delivery delivery = new Delivery();
        delivery.setDelivery_number(null); //운송장 번호 = 초기값 null
        delivery.setDelivery_company(null); //택배사 = 초기값 null
        delivery.setDelivery_address(order.getDelivery()
                                          .getDelivery_address()); // 배송지 = 구매자가 입력한 주소
        
        delivery = deliveryRepository.save(delivery);
        order.setDelivery(delivery);
    }
    
    private void setOrderDetails(Order order) {
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            orderDetail.setOrder(order); // 해당 주문에 연결
            orderDetail.setOrder_price(orderDetail.getProduct()
                                                  .getProduct_price()); // 주문 가격 설정
            // 재고 차감
            Product product = orderDetail.getProduct();
            product.setProduct_stock(product.getProduct_stock() - orderDetail.getProduct_quantity());
            productRepository.save(product); // 재고 업데이트
        }
    }
    
    private double calculateTotalPrice(Order order) {
        double totalPrice = 0;
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            totalPrice += orderDetail.getProduct()
                                     .getProduct_price() * orderDetail.getProduct_quantity();
        }
        return totalPrice;
    }
    
    public Order cancelOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                                     .orElseThrow(() -> new ResourceNotFoundException("ERROR: " + orderId));
        
        order.setOrder_state(OrderState.CANCELLED);
        return orderRepository.save(order);
    }
    
    public Page<Order> getAllOrderList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.orderRepository.findAll(pageable);
    }
    
    public Order updateOrderState(Integer orderId, Map<String, Object> updateFields) {
        Order order = orderRepository.findById(orderId)
                                     .orElseThrow(() -> new RuntimeException("Order not found"));
        
        if (updateFields.containsKey("orderState")) {
            order.setOrder_state(OrderState.valueOf(updateFields.get("orderState")
                                                                .toString()));
        }
        
        return orderRepository.save(order);
    }
}
