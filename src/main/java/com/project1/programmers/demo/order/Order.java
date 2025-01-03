package com.project1.programmers.demo.order;

import com.project1.programmers.demo.delivery.Address;
import com.project1.programmers.demo.delivery.Delivery;
import global.jpa.entity.BaseTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orders")
public class Order extends BaseTime {
    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private Boolean isBatchProcessed;

    @Embedded
    private Address address;

    @ManyToOne
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderList;

}
