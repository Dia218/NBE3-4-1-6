package org.team6.coffeebeanery.order;

import org.springframework.stereotype.Component;
import org.team6.coffeebeanery.common.constant.OrderStatus;
import org.team6.coffeebeanery.common.model.Address;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.model.OrderDetail;
import org.team6.coffeebeanery.order.repository.OrderDetailRepository;
import org.team6.coffeebeanery.order.repository.OrderRepository;
import org.team6.coffeebeanery.product.model.Product;
import org.team6.coffeebeanery.product.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderTestDataUtils {
    
    public static void createTestOrders(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
                                        ProductRepository productRepository) {
        // 테스트용 상품 정보 가져오기
        List<Product> products = productRepository.findAll();
        if (products.size() < 3) {
            throw new IllegalStateException(
                    "테스트 주문을 생성하려면 최소 3개의 상품이 필요합니다. ProductTestDataUtils를 사용하여 테스트 상품을 추가하세요.");
        }
        
        // 테스트 데이터 생성 호출
        createOrder(orderRepository, orderDetailRepository, "customer1@example.com", OrderStatus.ORDERED,
                    products.get(0), 2, 10000L, products.get(1), 1, 15000L,
                    new Address("경기도 성남시 분당구 불정로 5", "초록빌딩", "123-45"));
        
        createOrder(orderRepository, orderDetailRepository, "customer2@example.com", OrderStatus.PREPARING,
                    products.get(1), 3, 15000L, products.get(2), 2, 12000L,
                    new Address("경기도 성남시 분당구 판교로 256번길 3", "게임오피스", "543-21"));
        
        createOrder(orderRepository, orderDetailRepository, "customer3@example.com", OrderStatus.DELIVERED,
                    products.get(2), 3, 12000L, products.get(0), 4, 10000L,
                    new Address("서울시 송파구 위례성대로 1", "민족빌딩", "789-01"));
        
        createOrder(orderRepository, orderDetailRepository, "customer3@example.com", OrderStatus.CANCELLED,
                    products.get(2), 3, 12000L, products.get(1), 2, 15000L,
                    new Address("제주특별자치도 제주시 첨단로 141", "노란아파트", "789-01"));
    }
    
    private static void createOrder(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
                                    String customerEmail, OrderStatus orderStatus, Product product1, int quantity1,
                                    Long price1, Product product2, int quantity2, Long price2, Address address) {
        // Order 생성
        Order order = new Order();
        order.setCustomerEmail(customerEmail);
        order.setOrderStatus(orderStatus);
        order.setOrderCreatedAt(LocalDateTime.now());
        order.setAddress(address);
        
        // Order 저장
        order = orderRepository.save(order);
        
        // 첫 번째 OrderDetail 생성
        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setOrder(order);
        orderDetail1.setProduct(product1);
        orderDetail1.setProductQuantity(quantity1);
        orderDetail1.setOrderPrice(price1 * quantity1);
        
        // 두 번째 OrderDetail 생성
        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setOrder(order);
        orderDetail2.setProduct(product2);
        orderDetail2.setProductQuantity(quantity2);
        orderDetail2.setOrderPrice(price2 * quantity2);
        
        // OrderDetail 저장
        orderDetailRepository.save(orderDetail1);
        orderDetailRepository.save(orderDetail2);
        
        // Order 총 금액 업데이트
        Long totalPrice = orderDetail1.getOrderPrice() + orderDetail2.getOrderPrice();
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
    }
    
    public static void deleteTestOrders(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        orderDetailRepository.deleteAll();
        orderRepository.deleteAll();
    }
}