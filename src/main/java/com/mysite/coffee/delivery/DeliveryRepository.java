package com.mysite.coffee.delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByDeliveryStatusAndStartTimeBetween(DeliveryStatus deliveryStatus, LocalDateTime startTimeAfter, LocalDateTime startTimeBefore);
}
