package org.team6.coffeebeanery.delivery.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.team6.coffeebeanery.delivery.dto.DeliveryDTO;
import org.team6.coffeebeanery.delivery.model.Delivery;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    DeliveryMapper INSTANCE = Mappers.getMapper(DeliveryMapper.class);

    @Mapping(source = "order.orderId", target = "orderId")
        // Delivery에서 Order - orderId
    DeliveryDTO toDTO(Delivery delivery);

    @Mapping(source = "orderId", target = "order.orderId")
        // DeliveryDTO에서 orderId - Order
    Delivery toEntity(DeliveryDTO deliveryDTO);

}
