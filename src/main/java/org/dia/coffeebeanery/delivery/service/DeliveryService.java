package org.dia.coffeebeanery.delivery.service;

import lombok.RequiredArgsConstructor;
import org.dia.coffeebeanery.delivery.model.Delivery;
import org.dia.coffeebeanery.delivery.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    
    public Delivery updateDeliveryInfo(Integer deliveryId, Delivery updatedDelivery) {
        Delivery existingDelivery = deliveryRepository.findById(deliveryId)
                                                      .orElseThrow(() -> new RuntimeException("Delivery not found"));
        
        existingDelivery.setDelivery_company(updatedDelivery.getDelivery_company());
        existingDelivery.setDelivery_number(updatedDelivery.getDelivery_number());
        
        return deliveryRepository.save(existingDelivery);
    }
}
