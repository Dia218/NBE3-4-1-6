package org.team6.coffeebeanery.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.team6.coffeebeanery.common.constant.OrderStatus;
import org.team6.coffeebeanery.common.model.Address;
import org.team6.coffeebeanery.delivery.model.Delivery;
import org.team6.coffeebeanery.order.model.OrderDetail;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderDTO {
    private Integer orderId; //주문 ID
    
    private String customerEmail; //고객 이메일
    
    private Address address; // 주소 합친 것
    
    private LocalDateTime orderCreatedAt; //주문 날짜 및 시간
    
    private Double totalPrice; //총 주문 금액
    
    private OrderStatus orderStatus; //주문 상태
    
    private Delivery delivery; //연결된 배송
    
    private List<OrderDetail> orderDetails;
}