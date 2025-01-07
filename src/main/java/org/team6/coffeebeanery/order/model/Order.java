package org.team6.coffeebeanery.order.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.team6.coffeebeanery.common.constant.OrderStatus;
import org.team6.coffeebeanery.delivery.model.Delivery;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId; //주문 ID
    
    @Column(length = 254)
    private String customerEmail; //고객 이메일
    
    private String baseAddress; //기본 주소
    
    private String detailAddress; //상세 주소
    
    private String zipCode; //우편 주소
    
    private LocalDateTime orderCreatedAt; //주문 날짜 및 시간
    
    private Double totalPrice; //총 주문 금액
    
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문 상태
    
    @ManyToOne
    private Delivery delivery; //연결된 배송
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE) //주문 상세(상품) 목록
    private List<OrderDetail> orderDetails;
}