package org.team6.coffeebeanery.order.model;

import jakarta.persistence.*;
import lombok.*;
import org.team6.coffeebeanery.product.model.Product;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId; //주문 상세 ID
    
    @Column(nullable = false)
    private Integer productQuantity; //주문 상품 수량
    
    @Column(nullable = false)
    private Long orderPrice; //주문 시점 가격
    
    @ManyToOne
    private Product product; //주문 상품
    
    @ManyToOne
    private Order order; //소속된 주문
}