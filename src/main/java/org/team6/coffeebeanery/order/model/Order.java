package org.team6.coffeebeanery.order.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.team6.coffeebeanery.common.constant.OrderStatus;
import org.team6.coffeebeanery.common.model.Address;
import org.team6.coffeebeanery.delivery.model.Delivery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId; //주문 ID

    @Column(length = 254, nullable = false)
    private String customerEmail; //고객 이메일

    @Embedded
    private Address address; // 상세 주소 합친거

    @CreatedDate
    private LocalDateTime orderCreatedAt; //주문 날짜 및 시간

    @Column(nullable = false)
    private Long totalPrice; //총 주문 금액

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문 상태

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Delivery delivery; //연결된 배송

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) //주문 상세(상품) 목록
    private List<OrderDetail> orderDetails = new ArrayList<>();
}