package com.mysite.coffee.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DeliveryStatusScheduler {

    private final DeliveryService deliveryService;

    @Scheduled(cron = "0 0 14 * * *") // 매일 오후 2시 실행
    @Transactional
    public void processDeliveryUpdates() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = now.toLocalDate().atTime(14, 0).minusDays(1);
        LocalDateTime endTime = startTime.plusDays(1);

        // 배송 상태 PREPARING -> SHIPPED 변경
        List<Delivery> preparingDeliveries = deliveryService.findByDeliveryStatusAndStartTimeBetween(DeliveryStatus.PREPARING, startTime, endTime);
        for (Delivery preparingDelivery : preparingDeliveries) {
            preparingDelivery.setDeliveryStatus(DeliveryStatus.SHIPPED);
            deliveryService.save(preparingDelivery);
        }

        // 하루 전 SHIPPED 배송 상태 -> DELIVERED 변경
        LocalDateTime yesterdayStart = startTime.minusDays(1);
        LocalDateTime yesterdayEnd = endTime.minusDays(1);

        List<Delivery> shippedDeliveries = deliveryService.findByDeliveryStatusAndStartTimeBetween(DeliveryStatus.SHIPPED, yesterdayStart, yesterdayEnd);
        for (Delivery shippedDelivery : shippedDeliveries) {
            shippedDelivery.setDeliveryStatus(DeliveryStatus.DELIVERED);
            deliveryService.save(shippedDelivery);
        }
        System.out.println("스케줄러 실행됨: " + LocalDateTime.now());
    }

}
