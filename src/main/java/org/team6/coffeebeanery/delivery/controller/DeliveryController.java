package org.team6.coffeebeanery.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.team6.coffeebeanery.delivery.dto.DeliveryDTO;
import org.team6.coffeebeanery.delivery.mapper.DeliveryMapper;
import org.team6.coffeebeanery.delivery.model.Delivery;
import org.team6.coffeebeanery.delivery.service.DeliveryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveries() {
        List<Delivery> deliveries = deliveryService.getAllDeliveries();
        List<DeliveryDTO> deliveryDTOs = deliveries.stream()
                .map(DeliveryMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(deliveryDTOs);
    }

    @GetMapping("/{deliveryId}")
    public ResponseEntity<DeliveryDTO> getDeliveryById(@PathVariable Long deliveryId) {
        Delivery delivery = deliveryService.getDeliveryById(deliveryId);
        DeliveryDTO deliveryDTO = DeliveryMapper.INSTANCE.toDTO(delivery);
        return ResponseEntity.ok(deliveryDTO);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<DeliveryDTO> getDeliveryByOrderId(@PathVariable Long orderId) {
        try {
            System.out.println("Received request for orderId: " + orderId);
            Delivery delivery = deliveryService.getDeliveryByOrderId(orderId);
            DeliveryDTO deliveryDTO = DeliveryMapper.INSTANCE.toDTO(delivery);
            return ResponseEntity.ok(deliveryDTO);
        } catch (Exception e) {
            System.err.println("Error processing request: " + e.getMessage());
            throw e;
        }
    }


}