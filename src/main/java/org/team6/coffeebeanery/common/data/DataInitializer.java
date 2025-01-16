package org.team6.coffeebeanery.common.data;

import jakarta.annotation.PreDestroy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.team6.coffeebeanery.delivery.repository.DeliveryRepository;
import org.team6.coffeebeanery.order.repository.OrderDetailRepository;
import org.team6.coffeebeanery.order.repository.OrderRepository;
import org.team6.coffeebeanery.product.repository.ProductRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    
    // 생성자 주입
    public DataInitializer(DeliveryRepository deliveryRepository, OrderRepository orderRepository,
                           OrderDetailRepository orderDetailRepository, ProductRepository productRepository) {
        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
    }
    
    @Override
    public void run(String... args) throws Exception { // 애플리케이션 시작 시 실행
        // 모든 테이블 데이터 삭제 (순서 주의)
        orderRepository.deleteAll();  // 주문 삭제
        orderDetailRepository.deleteAll();  // 주문 상세 삭제
        deliveryRepository.deleteAll();  // 배송 데이터 삭제
        productRepository.deleteAll();  // 상품 데이터 삭제
        
        // Test 데이터 삽입
        ProductTestDataUtils.createTestProducts(productRepository);   // 제품 데이터 삽입
        DeliveryTestDataUtils.createTestDeliveries(deliveryRepository);  // 배송 데이터 삽입
        OrderTestDataUtils.createTestOrders(orderRepository, orderDetailRepository, productRepository);  // 주문 데이터 삽입
        
        System.out.println("초기 데이터 삽입 완료");
    }
    
    @PreDestroy
    public void cleanUp() { // 애플리케이션 종료 시 실행
        // 데이터 삭제
        DeliveryTestDataUtils.deleteTestDeliveries(deliveryRepository);
        OrderTestDataUtils.deleteTestOrders(orderRepository, orderDetailRepository);
        ProductTestDataUtils.deleteTestProducts(productRepository); // 순서 주의
        
        System.out.println("초기 데이터 삭제 완료");
    }
}
