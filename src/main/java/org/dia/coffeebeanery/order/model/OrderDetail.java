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
    private Integer order_detail_id; //주문 상세 ID
    
    private Integer product_quantity; //주문 상품 수량
    
    private Integer order_price; //주문 시점 가격
    
    @OneToOne
    private Product product; //해당 상품
    
    @ManyToOne
    private Order order; //소속된 주문
}
