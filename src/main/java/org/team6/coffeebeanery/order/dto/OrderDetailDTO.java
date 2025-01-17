package org.team6.coffeebeanery.order.dto;

import lombok.Builder;
import org.team6.coffeebeanery.order.model.OrderDetail;
import org.team6.coffeebeanery.product.dto.ProductDTO;

@Builder
public record OrderDetailDTO(
        Long orderDetailId,      // 주문 상세 ID
        Integer productQuantity,  // 주문 상품 수량
        Long orderPrice,         // 주문 시점 가격
        ProductDTO productDTO      // 주문 상품
) {
    public static OrderDetailDTO toDTO(OrderDetail orderDetail) {
        if (orderDetail == null) {
            return null;
        }

        return OrderDetailDTO.builder()
                .orderDetailId(orderDetail.getOrderDetailId())
                .productQuantity(orderDetail.getProductQuantity())
                .orderPrice(orderDetail.getOrderPrice())
                .productDTO(ProductDTO.toDTO(orderDetail.getProduct()))
                .build();
    }
}