package org.dia.coffeebeanery.order.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.dia.coffeebeanery.delivery.model.Delivery;
import org.dia.coffeebeanery.order.constant.OrderState;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_id; //주문 ID
    
    @Column(length = 254)
    private String customer_email; //고객 이메일
    
    private LocalDateTime order_created_at; //주문 날짜 및 시간
    
    private Double total_price; //총 주문 금액
    
    @Enumerated(EnumType.STRING)
    private OrderState order_state; //주문 상태
    
    @ManyToOne
    private Delivery delivery; //연결된 배송
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE) //주문 상세(상품) 목록
    private List<OrderDetail> orderDetails;
}
