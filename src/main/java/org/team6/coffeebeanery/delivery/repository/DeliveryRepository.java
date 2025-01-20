package org.team6.coffeebeanery.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team6.coffeebeanery.delivery.model.Delivery;
import org.team6.coffeebeanery.order.model.Order;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByOrder(Order order);
    boolean existsByOrder(Order order);
}