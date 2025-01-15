package org.team6.coffeebeanery.delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeliveryDTO {
    private Long deliveryId; // 배송 ID
    private String deliveryNumber; // 운송장 번호
    private String deliveryCompany; // 택배사
    private Long orderId; // 주문 ID (추가)
}