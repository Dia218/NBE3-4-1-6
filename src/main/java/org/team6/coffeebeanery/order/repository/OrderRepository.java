package org.team6.coffeebeanery.order.repository;

import org.springframework.data.domain.Sort;
import org.team6.coffeebeanery.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByCustomerEmail(String customerEmail, Sort sort);
}
