package org.team6.coffeebeanery.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team6.coffeebeanery.delivery.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}