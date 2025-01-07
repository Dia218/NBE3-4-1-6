package org.dia.coffeebeanery.order.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.dia.coffeebeanery.product.model.Product;

@Getter
@Setter
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailId; //주문 상세 ID
    
    private Integer productQuantity; //주문 상품 수량
    
    private Integer orderPrice; //주문 시점 가격
    
    @OneToOne
    private Product product; //주문 상품
    
    @ManyToOne
    private Order order; //소속된 주문
}