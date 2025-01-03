package com.project1.programmers.demo.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByEmail(String email);
    Page<Order> findAllByEmail(String email, Pageable pageable);
    Page<Order> findAll(Pageable pageable);

    List<Order> findByCreatedAtBetweenAndIsBatchProcessedFalse(
        LocalDateTime start, 
        LocalDateTime end
    );

    List<Order> findByCreatedAtBetween(
            LocalDateTime start,
            LocalDateTime end
    );
}
