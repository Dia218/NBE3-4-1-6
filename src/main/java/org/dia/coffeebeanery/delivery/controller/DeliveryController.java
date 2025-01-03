package org.dia.coffeebeanery.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.dia.coffeebeanery.delivery.model.Delivery;
import org.dia.coffeebeanery.delivery.service.DeliveryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DeliveryController {
    private final DeliveryService deliveryService;
    
    @PutMapping("/admin/orders/{deliveryId}") //판매자용) 배송 정보 변경
    public Delivery updateDeliveryInfo(@PathVariable Integer deliveryId, @RequestBody Delivery updatedDelivery) {
        return deliveryService.updateDeliveryInfo(deliveryId, updatedDelivery);
    }
}
