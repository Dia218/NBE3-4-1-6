package org.team6.coffeebeanery.order.dto;

import lombok.Builder;
import org.team6.coffeebeanery.order.model.OrderDetail;
import org.team6.coffeebeanery.product.dto.ProductDTO;

@Builder
public record OrderDetailDTO(
        Long orderDetailId,      // 주문 상세 ID
        Integer productQuantity,  // 주문 상품 수량
        Long orderPrice,         // 주문 시점 가격
        ProductDTO product      // 주문 상품 (Product ID)
) {
    public static OrderDetailDTO toDTO(OrderDetail orderDetail) {
        if (orderDetail == null) {
            return null;
        }

        return new OrderDetailDTO(
                orderDetail.getOrderDetailId(),
                orderDetail.getProductQuantity(),
                orderDetail.getOrderPrice(),
                ProductDTO.toDTO(orderDetail.getProduct())
        );
    }
}