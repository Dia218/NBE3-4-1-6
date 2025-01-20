package org.team6.coffeebeanery.delivery.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.team6.coffeebeanery.common.constant.OrderStatus;
import org.team6.coffeebeanery.delivery.model.Delivery;
import org.team6.coffeebeanery.delivery.repository.DeliveryRepository;
import org.springframework.stereotype.Service;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;

    public Delivery getDeliveryById(Long deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found with id: " + deliveryId));
    }

    public Delivery getDeliveryByOrderId(Long orderId) {
        return deliveryRepository.findByOrderOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found for order id: " + orderId));
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public String generateRandomTrackingNumber() {
        Random random = new Random();
        StringBuilder trackingNumber = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            trackingNumber.append(random.nextInt(10));
        }
        return trackingNumber.toString();
    }

    public String generateCourierName() {
        Random random = new Random();
        char firstChar = (char) (random.nextInt(11172) + 44032);
        char secondChar = (char) (random.nextInt(11172) + 44032);
        return "" + firstChar + secondChar + "택배";
    }

    public Delivery createDelivery(Order order) {
        // 이미 배송 정보가 존재하는지 확인
        Optional<Delivery> existingDelivery = deliveryRepository.findByOrderOrderId(order.getOrderId());
        if (existingDelivery.isPresent()) {
            return existingDelivery.get();
        }

        Delivery delivery = Delivery.builder()
                .deliveryNumber(generateRandomTrackingNumber())
                .deliveryCompany(generateCourierName())
                .order(order)
                .build();

        return deliveryRepository.save(delivery);
    }

    @Transactional
    @Scheduled(cron = "0 0 14 * * *")
    public void completeDelivery() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);
        LocalDateTime twoDaysBefore = now.minusDays(2);

        List<Order> orders = orderRepository.findByOrderCreatedAtBetween(
                yesterday.withHour(14).withMinute(0).withSecond(0),
                twoDaysBefore.withHour(14).withMinute(0).withSecond(0));

        for (Order order : orders) {
            order.setOrderStatus(OrderStatus.DELIVERED);
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 14 * * *")
    public void createDeliveryBatch() {
        System.out.println("run");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);

        List<Order> orders = orderRepository.findByOrderCreatedAtBetweenAndOrderStatus(
                yesterday,
                now,
                OrderStatus.ORDERED);

        for (Order order : orders) {
            if (order.getDelivery() == null) {
                Delivery delivery = createDelivery(order);
                order.setDelivery(delivery);
                order.setOrderStatus(OrderStatus.PREPARING);
            }
        }
    }
}