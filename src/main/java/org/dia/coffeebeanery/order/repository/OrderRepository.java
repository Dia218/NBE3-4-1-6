package org.dia.coffeebeanery.order.repository;

import org.dia.coffeebeanery.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findByCustomer_email(String email, Pageable pageable);
}