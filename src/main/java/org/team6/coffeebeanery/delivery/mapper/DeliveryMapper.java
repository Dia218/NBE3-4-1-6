package org.team6.coffeebeanery.delivery.mapper;

import org.mapstruct.Mapper;
import org.team6.coffeebeanery.delivery.dto.DeliveryDTO;
import org.team6.coffeebeanery.delivery.model.Delivery;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    
    DeliveryDTO toDTO(Delivery delivery);
    
    Delivery toEntity(DeliveryDTO deliveryDTO);
}