package org.dia.coffeebeanery.delivery.repository;

import org.dia.coffeebeanery.delivery.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
}
