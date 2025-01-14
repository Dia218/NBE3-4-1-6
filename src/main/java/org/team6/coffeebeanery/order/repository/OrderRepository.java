package org.team6.coffeebeanery.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team6.coffeebeanery.order.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 이메일로 주문 리스트 검색 쿼리 메서드
    List<Order> findByCustomerEmail(String customerEmail);

    // 이메일 존재 여부 확인 쿼리 메서드
    boolean existsByCustomerEmail(String customerEmail);
}
