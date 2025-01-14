package org.team6.coffeebeanery.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.team6.coffeebeanery.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByCustomerEmail(String customerEmail, Pageable pageable);
}