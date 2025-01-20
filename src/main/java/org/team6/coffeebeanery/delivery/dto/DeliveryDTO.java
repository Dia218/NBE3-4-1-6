package org.team6.coffeebeanery.delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.team6.coffeebeanery.order.dto.OrderDTO;
import org.team6.coffeebeanery.order.model.Order;

@Getter
@Setter
@Builder
public class DeliveryDTO {
    private Long deliveryId; // 배송 ID
    private String deliveryNumber; // 운송장 번호
    private String deliveryCompany; // 택배사
    private Long orderId; // 주문 ID (추가)
    private OrderDTO orderDTO;
    private Order order;
}