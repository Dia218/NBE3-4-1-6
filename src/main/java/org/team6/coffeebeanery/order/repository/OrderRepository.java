package org.team6.coffeebeanery.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.team6.coffeebeanery.order.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByCustomerEmail(String customerEmail, Sort sort);
    Page<Order> findAllBy(Pageable pageable);
}
