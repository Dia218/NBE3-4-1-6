package com.mysite.coffee.order;

import com.mysite.coffee.delivery.Delivery;
import com.mysite.coffee.delivery.DeliveryService;
import com.mysite.coffee.orderDetail.OrderDetail;
import com.mysite.coffee.orderDetail.OrderDetailService;
import com.mysite.coffee.product.Product;
import com.mysite.coffee.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mysite.coffee.delivery.DeliveryStatus.PREPARING;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;
    private final OrderDetailService orderDetailService;
    private final ProductService productService;


    @Transactional
    public Order save(OrderRequestDto orderRequestDto) {
        // Delivery 저장
        Delivery delivery = orderRequestDto.getDelivery();
        delivery.setDeliveryStatus(PREPARING);
        deliveryService.save(delivery);

        // Order 객체 생성 먼저
        Order order = new Order();
        order.setEmail(orderRequestDto.getEmail());
        order.setTotalPrice(orderRequestDto.getTotalPrice());
        order.setDelivery(delivery);

        // OrderDetail 꺼내기
        List<OrderDetail> orderDetails = orderRequestDto.getOrderDetails();

        // OrderDetail에 Order정보 저장
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrder(order);
            // orderDetail quantity만큼 해당 product stock 수량 변경
            Product product = productService.findById(orderDetail.getProduct().getId());
            orderDetail.setProduct(product);
            product.decreaseStock(orderDetail.getQuantity());
        }

        // OrderDetail 저장
        orderDetailService.save(orderDetails);

        // Order에 OrderDetail정보 저장
        order.setOrderDetails(orderDetails);

        // OrderStatus Ordered로 저장
        order.setOrderStatus(OrderStatus.ORDERED);

        // Order 저장
        return orderRepository.save(order);
    }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    public List<Order> findAllByEmail(String email) {
        return orderRepository.findAllByEmail(email);
    }
}
