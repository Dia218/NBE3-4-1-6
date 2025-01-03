package com.mysite.coffee.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;


    public Delivery save(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public List<Delivery> findByDeliveryStatusAndStartTimeBetween(DeliveryStatus deliveryStatus, LocalDateTime startTime, LocalDateTime endTime) {
        return deliveryRepository.findByDeliveryStatusAndStartTimeBetween(deliveryStatus, startTime, endTime);
    }

    public Optional<Delivery> findById(Long id) {
        return deliveryRepository.findById(id);
    }
}
