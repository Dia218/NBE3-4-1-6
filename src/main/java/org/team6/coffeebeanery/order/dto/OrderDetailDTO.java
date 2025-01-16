package org.team6.coffeebeanery.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.team6.coffeebeanery.order.model.OrderDetail;
import org.team6.coffeebeanery.product.dto.ProductDTO;

@Getter
@Setter
@Builder
public class OrderDetailDTO {
    private Long orderDetailId; //주문 상세 ID
    private Integer productQuantity; //주문 상품 수량
    private Long orderPrice; //주문 시점 가격
    private ProductDTO product; //주문 상품 (Product ID)
    private Long orderId; //주문 ID (Order ID)
    
    public static OrderDetailDTO toDTO(OrderDetail orderDetail) {
        if (orderDetail == null) {
            return null;
        }
        
        return OrderDetailDTO.builder()
                             .orderDetailId(orderDetail.getOrderDetailId())
                             .productQuantity(orderDetail.getProductQuantity())
                             .orderPrice(orderDetail.getOrderPrice())
                             .product(ProductDTO.toDto(orderDetail.getProduct()))
                             .orderId(orderDetail.getOrder() != null ? orderDetail.getOrder()
                                                                                  .getOrderId() : null) // 주문 ID 매핑
                             .build();
    }
}