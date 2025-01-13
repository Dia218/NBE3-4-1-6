package org.team6.coffeebeanery.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team6.coffeebeanery.order.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
