package org.team6.coffeebeanery.delivery.dto;

import lombok.*;
import org.team6.coffeebeanery.common.constant.OrderStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor  // 추가
@AllArgsConstructor // 추가
public class DeliveryDTO {
    private Long deliveryId;
    private String deliveryNumber;
    private String deliveryCompany;
    private Long orderId;
    private SimpleOrderInfo order;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor  // 추가
    @AllArgsConstructor // 추가
    public static class SimpleOrderInfo {
        private Long orderId;
        private OrderStatus orderStatus;
    }
}