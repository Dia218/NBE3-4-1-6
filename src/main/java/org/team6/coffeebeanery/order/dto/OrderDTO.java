package org.team6.coffeebeanery.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.team6.coffeebeanery.common.constant.OrderStatus;
import org.team6.coffeebeanery.common.model.Address;
import org.team6.coffeebeanery.order.model.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderDTO {
    private Long orderId;
    private String customerEmail;

    // Address 정보
    private String baseAddress;
    private String detailAddress;
    private String zipCode;

    private LocalDateTime orderCreatedAt;
    private Long totalPrice;
    private OrderStatus orderStatus;

    // Delivery 정보
    private Long deliveryId;

    // OrderDetail 정보
    private List<OrderDetailDTO> orderDetails;

    public static OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }

        // Address 변환
        Address address = order.getAddress();
        String zipCode = address != null ? address.getZipCode() : null;
        String baseAddress = address != null ? address.getBaseAddress() : null;
        String detailAddress = address != null ? address.getDetailAddress() : null;

        // Delivery ID 변환
        Long deliveryId = order.getDelivery() != null ? order.getDelivery().getDeliveryId() : null;

        // OrderDetails 변환
        List<OrderDetailDTO> orderDetails = order.getOrderDetails() != null
                ? order.getOrderDetails().stream()
                .map(OrderDetailDTO::toDTO)
                .toList()
                : null;

        // OrderDTO 빌드
        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .customerEmail(order.getCustomerEmail())
                .baseAddress(baseAddress)
                .detailAddress(detailAddress)
                .zipCode(zipCode)
                .orderCreatedAt(order.getOrderCreatedAt())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .deliveryId(deliveryId)
                .orderDetails(orderDetails)
                .build();
    }
}