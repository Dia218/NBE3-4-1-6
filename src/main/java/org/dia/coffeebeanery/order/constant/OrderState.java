package org.dia.coffeebeanery.order.constant;

import lombok.Getter;

@Getter
public enum OrderState {
    ORDERED("주문 완료"),
    PREPARING("배송 준비 중"),
    SHIPPED("배송 접수"),
    DELIVERED("배송 완료"),
    CANCELLED("주문 취소");
    
    private final String state;
    
    OrderState(String state) {
        this.state = state;
    }
    
}