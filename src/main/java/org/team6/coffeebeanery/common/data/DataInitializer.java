package org.team6.coffeebeanery.common.data;

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
    public void run(String... args) throws Exception {

        // 테스트 데이터 순차적으로 생성
        ProductTestDataUtils.createTestProducts(productRepository);   // 1. 상품 데이터
        OrderTestDataUtils.createTestOrders(orderRepository, orderDetailRepository, productRepository);  // 2. 주문 데이터
        DeliveryTestDataUtils.createTestDeliveries(deliveryRepository, orderRepository);  // 3. 배송 데이터

        System.out.println("초기 데이터 삽입 완료");
    }
}
