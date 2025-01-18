package org.team6.coffeebeanery.common.data;

import org.springframework.stereotype.Component;
import org.team6.coffeebeanery.common.constant.OrderStatus;
import org.team6.coffeebeanery.delivery.model.Delivery;
import org.team6.coffeebeanery.delivery.repository.DeliveryRepository;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.repository.OrderRepository;

import java.util.List;
import java.util.Random;

@Component
public class DeliveryTestDataUtils {
    public static void createTestDeliveries(DeliveryRepository deliveryRepository, OrderRepository orderRepository) {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new IllegalStateException("테스트 배송을 생성하려면 주문 데이터가 필요합니다.");
        }

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            // 주문 취소 상태가 아닌 경우에만 배송 정보 생성
            if (order.getOrderStatus() != OrderStatus.CANCELLED) {
                Delivery delivery = new Delivery();
                delivery.setDeliveryNumber(generateDeliveryNumber());
                delivery.setDeliveryCompany(generateDeliveryCompany());
                delivery.setOrder(order); // Order와 Delivery 연결
                deliveryRepository.save(delivery);
            }
        }
    }

    private static String generateDeliveryNumber() {
        return String.format("%010d", new Random().nextInt(1000000000));
    }

    private static String generateDeliveryCompany() {
        String[] companies = {"대운택배", "한잔택배", "CC택배"};
        return companies[new Random().nextInt(companies.length)];
    }
}
