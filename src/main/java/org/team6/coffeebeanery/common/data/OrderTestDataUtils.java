package org.team6.coffeebeanery.common.data;

import org.springframework.stereotype.Component;
import org.team6.coffeebeanery.common.constant.OrderStatus;
import org.team6.coffeebeanery.common.model.Address;
import org.team6.coffeebeanery.delivery.model.Delivery;
import org.team6.coffeebeanery.delivery.repository.DeliveryRepository;
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

    public static void createTestOrders(
            OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository,
            DeliveryRepository deliveryRepository,
            ProductRepository productRepository
    ) {
        // 테스트용 상품 정보 가져오기
        List<Product> products = productRepository.findAll();
        if (products.size() < 3) {
            throw new IllegalStateException(
                    "테스트 주문을 생성하려면 최소 3개의 상품이 필요합니다. ProductTestDataUtils를 사용하여 테스트 상품을 추가하세요.");
        }

        // 4개의 이메일에 대해 데이터 생성
        createOrdersWithAllStatuses(orderRepository, orderDetailRepository, deliveryRepository,
                "customer1@example.com", products.get(0), 2, 10000L, products.get(1), 1, 15000L,
                new Address("경기도 성남시 분당구 불정로 5", "초록빌딩", "12345"));

        createOrdersWithAllStatuses(orderRepository, orderDetailRepository, deliveryRepository,
                "customer2@example.com", products.get(1), 3, 15000L, products.get(2), 2, 12000L,
                new Address("경기도 성남시 분당구 판교로 256번길 3", "게임오피스", "54321"));

        createOrdersWithAllStatuses(orderRepository, orderDetailRepository, deliveryRepository,
                "customer3@example.com", products.get(2), 3, 12000L, products.get(0), 4, 10000L,
                new Address("서울시 송파구 위례성대로 1", "민족빌딩", "78901"));

        createOrdersWithAllStatuses(orderRepository, orderDetailRepository, deliveryRepository,
                "customer4@example.com", products.get(0), 1, 10000L, products.get(2), 2, 12000L,
                new Address("제주특별자치도 제주시 첨단로 141", "노란아파트", "98765"));
    }

    private static void createOrdersWithAllStatuses(
            OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository,
            DeliveryRepository deliveryRepository,
            String customerEmail,
            Product product1,
            int quantity1,
            Long price1,
            Product product2,
            int quantity2,
            Long price2,
            Address address
    ) {
        for (OrderStatus status : OrderStatus.values()) {
            createOrder(orderRepository, orderDetailRepository, deliveryRepository,
                    customerEmail, status, product1, quantity1, price1, product2, quantity2, price2, address);
        }
    }

    private static void createOrder(
            OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository,
            DeliveryRepository deliveryRepository,
            String customerEmail,
            OrderStatus orderStatus,
            Product product1,
            int quantity1,
            Long price1,
            Product product2,
            int quantity2,
            Long price2,
            Address address
    ) {
        // 첫 번째 OrderDetail 생성
        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProduct(product1);
        orderDetail1.setProductQuantity(quantity1);
        orderDetail1.setOrderPrice(price1 * quantity1);

        // 두 번째 OrderDetail 생성
        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProduct(product2);
        orderDetail2.setProductQuantity(quantity2);
        orderDetail2.setOrderPrice(price2 * quantity2);

        // Order 총 금액 계산
        Long totalPrice = orderDetail1.getOrderPrice() + orderDetail2.getOrderPrice();

        // Order 생성
        Order order = new Order();
        order.setCustomerEmail(customerEmail);
        order.setOrderStatus(orderStatus);
        order.setOrderCreatedAt(LocalDateTime.now());
        order.setAddress(address);
        order.setTotalPrice(totalPrice);

        // Order 저장
        order = orderRepository.save(order);

        // OrderDetail과 Order 연결 후 저장
        orderDetail1.setOrder(order);
        orderDetail2.setOrder(order);
        orderDetailRepository.save(orderDetail1);
        orderDetailRepository.save(orderDetail2);

        // PREPARING, DELIVERED 상태에 대해 Delivery 생성
        if (orderStatus == OrderStatus.PREPARING || orderStatus == OrderStatus.DELIVERED) {
            createDelivery(deliveryRepository, order);
        }
    }

    private static void createDelivery(DeliveryRepository deliveryRepository, Order order) {
        Delivery delivery = Delivery.builder()
                .deliveryNumber(generateRandomTrackingNumber())
                .deliveryCompany(generateCourierName())
                .order(order)
                .build();

        // Delivery 저장
        deliveryRepository.save(delivery);

        // Order에 Delivery 연결 후 저장
        order.setDelivery(delivery);
        deliveryRepository.save(delivery); // 추가 저장
    }

    private static String generateRandomTrackingNumber() {
        return String.valueOf((long) (Math.random() * 1_000_000_000_000L));
    }

    private static String generateCourierName() {
        String[] courierNames = {"우체국택배", "롯데택배", "로젠택배", "한진택배", "CJ대한통운 택배"};
        return courierNames[(int) (Math.random() * courierNames.length)];
    }

    public static void deleteTestOrders(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
                                        DeliveryRepository deliveryRepository) {
        deliveryRepository.deleteAll();
        orderDetailRepository.deleteAll();
        orderRepository.deleteAll();
    }
}
