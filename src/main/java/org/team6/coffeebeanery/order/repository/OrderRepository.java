package org.team6.coffeebeanery.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.team6.coffeebeanery.order.model.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomerEmail(String email);
    Page<Order> findAllByCustomerEmail(String customerEmail, Pageable pageable);

}