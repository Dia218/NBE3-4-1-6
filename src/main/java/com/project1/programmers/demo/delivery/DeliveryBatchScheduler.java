package com.project1.programmers.demo.delivery;

import com.project1.programmers.demo.order.Order;
import com.project1.programmers.demo.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DeliveryBatchScheduler {

    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;

    @Transactional
    @Scheduled(cron = "0 0 14 * * *") // 매일 오후 2시에 실행
    public void createDeliveryBatch() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);
        
        // 전날 오후 2시부터 오늘 오후 2시까지의 미처리된 주문들 조회
        List<Order> orders = orderRepository.findByCreatedAtBetweenAndIsBatchProcessedFalse(
            yesterday.withHour(14).withMinute(0).withSecond(0),
            now.withHour(14).withMinute(0).withSecond(0)
        );

        // 배송 객체 생성 및 주문 상태 업데이트
        for (Order order : orders) {
            Delivery delivery = Delivery.builder()
                    .trackingNumber(DeliveryUtil.generateTrackingNumber())
                    .carrier("CJ 대한통운")
                    .build();
            
            deliveryRepository.save(delivery);
            
            order.setDelivery(delivery);
            order.setIsBatchProcessed(true);
        }
    }


    @Transactional
    @Scheduled(cron = "0 0 14 * * *")
    public void completeDelivery() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);
        LocalDateTime twoDaysBefore = now.minusDays(2);

        List<Order> orders = orderRepository.findByCreatedAtBetween(
                yesterday.withHour(14).withMinute(0).withSecond(0),
                twoDaysBefore.withHour(14).withMinute(0).withSecond(0));

        for(Order order : orders) {
            Delivery delivery = order.getDelivery();
            delivery.setStatus(DeliveryStatus.COMPLETED);
            deliveryRepository.save(delivery);
        }
    }
} 