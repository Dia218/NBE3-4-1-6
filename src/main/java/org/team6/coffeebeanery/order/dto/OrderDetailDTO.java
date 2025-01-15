package org.team6.coffeebeanery.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.team6.coffeebeanery.order.model.OrderDetail;

@Getter
@Setter
@Builder
public class OrderDetailDTO {
    private Long orderDetailId; //주문 상세 ID

    private Integer productQuantity; //주문 상품 수량

    private Long orderPrice; //주문 시점 가격

    private Long productId; //주문 상품

    public static OrderDetailDTO toDTO(OrderDetail orderDetail) {
        if (orderDetail == null) {
            return null;
        }

        return OrderDetailDTO.builder()
                .orderDetailId(orderDetail.getOrderDetailId())
                .productQuantity(orderDetail.getProductQuantity())
                .orderPrice(orderDetail.getOrderPrice())
                .productId(orderDetail.getProduct() != null ? orderDetail.getProduct().getProductId() : null)
                .build();
    }
}