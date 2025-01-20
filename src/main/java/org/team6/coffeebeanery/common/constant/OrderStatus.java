package org.team6.coffeebeanery.common.constant;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ORDERED("주문 완료"),
    PREPARING("배송 중"),
    DELIVERED("배송 완료"),
    CANCELLED("주문 취소");
    
    private final String status;
    
    OrderStatus(String status) {
        this.status = status;
    }
    
}