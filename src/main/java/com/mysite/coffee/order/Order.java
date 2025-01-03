package com.mysite.coffee.order;

import com.mysite.coffee.delivery.Delivery;
import com.mysite.coffee.orderDetail.OrderDetail;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private Integer totalPrice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Order(OrderRequestDto orderRequestDto) {
        this.setEmail(orderRequestDto.getEmail());
        this.setDelivery(orderRequestDto.getDelivery());
        this.setTotalPrice(orderRequestDto.getTotalPrice());
        this.setOrderStatus(OrderStatus.ORDERED);
    }
}
