package org.team6.coffeebeanery.order.dto;

import lombok.Builder;
import org.team6.coffeebeanery.common.constant.OrderStatus;
import org.team6.coffeebeanery.common.model.Address;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.model.OrderDetail;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDTO(
        Long orderId,
        String customerEmail,
        Address address,
        LocalDateTime orderCreatedAt,
        Long totalPrice,
        OrderStatus orderStatus,
        Long deliveryId,
        List<OrderDetailDTO> orderDetails) {
    public static OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }

        // Address 변환
        Address address = order.getAddress();
        if(address == null) {
            return null;
        }

        List<OrderDetail> orderDetails = order.getOrderDetails();
        if(orderDetails == null) {
            return null;
        }

        String zipCode = address.getZipCode();
        String baseAddress = address.getBaseAddress();
        String detailAddress = address.getDetailAddress();
        Long deliveryId = order.getDelivery() != null ? order.getDelivery().getDeliveryId() : null;
        List<OrderDetailDTO> orderDetailsDTO = orderDetails
                .stream()
                .map(OrderDetailDTO::toDTO)
                .toList();

        // OrderDTO 빌드
        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .customerEmail(order.getCustomerEmail())
                .address(address)
                .orderCreatedAt(order.getOrderCreatedAt())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .deliveryId(deliveryId)  // Delivery ID
                .orderDetails(orderDetailsDTO)
                .build();
    }
}