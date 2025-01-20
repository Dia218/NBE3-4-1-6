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
            // 배송 정보는 PREPARING, DELIVERED에만 있어야 정상
            if (order.getOrderStatus() == OrderStatus.PREPARING || order.getOrderStatus() == OrderStatus.DELIVERED) {
                Delivery delivery = new Delivery();
                delivery.setDeliveryNumber(generateDeliveryNumber());
                delivery.setDeliveryCompany(generateDeliveryCompany());
                delivery.setOrder(order); // Order와 Delivery 연결
                order.setDelivery(delivery); // Order에 Delivery 정보 기입

                deliveryRepository.save(delivery);
                orderRepository.save(order);
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
