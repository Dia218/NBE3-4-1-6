package org.team6.coffeebeanery.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.product.model.Product;

@Getter
@Setter
@Builder
public class OrderDetailDTO {
    private Long orderDetailId; //주문 상세 ID
    
    private Integer productQuantity; //주문 상품 수량
    
    private Integer orderPrice; //주문 시점 가격
    
    private Product product; //주문 상품
    
    private Order order; //소속된 주문
}