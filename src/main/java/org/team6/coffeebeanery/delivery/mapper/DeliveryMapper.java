package org.team6.coffeebeanery.delivery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.team6.coffeebeanery.delivery.dto.DeliveryDTO;
import org.team6.coffeebeanery.delivery.model.Delivery;
import org.team6.coffeebeanery.order.model.Order;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    DeliveryMapper INSTANCE = Mappers.getMapper(DeliveryMapper.class);

    @Mapping(target = "orderId", source = "order.orderId")
    @Mapping(target = "order", source = "order")
    DeliveryDTO toDTO(Delivery delivery);

    @Mapping(source = "orderId", target = "order.orderId")
    Delivery toEntity(DeliveryDTO deliveryDTO);

    // SimpleOrderInfo 매핑을 위한 메서드
    default DeliveryDTO.SimpleOrderInfo orderToSimpleOrderInfo(Order order) {
        if (order == null) {
            return null;
        }
        return DeliveryDTO.SimpleOrderInfo.builder()
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}