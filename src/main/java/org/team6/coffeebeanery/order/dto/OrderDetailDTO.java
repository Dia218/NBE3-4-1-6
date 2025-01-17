package org.team6.coffeebeanery.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.team6.coffeebeanery.order.model.OrderDetail;
import org.team6.coffeebeanery.product.dto.ProductDTO;
import org.team6.coffeebeanery.product.mapper.ProductMapper;
import org.team6.coffeebeanery.product.model.Product;

@Builder
public record OrderDetailDTO (
        Long orderDetailId, //주문 상세 ID
        Integer productQuantity, //주문 상품 수량
        Long orderPrice, //주문 시점 가격
        ProductDTO productDTO
)
    {
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