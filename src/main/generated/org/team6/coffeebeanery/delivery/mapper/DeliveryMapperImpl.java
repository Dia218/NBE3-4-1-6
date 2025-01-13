package org.team6.coffeebeanery.delivery.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.team6.coffeebeanery.delivery.dto.DeliveryDTO;
import org.team6.coffeebeanery.delivery.model.Delivery;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-13T17:32:11+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.1 (GraalVM Community)"
)
@Component
public class DeliveryMapperImpl implements DeliveryMapper {

    @Override
    public DeliveryDTO toDTO(Delivery delivery) {
        if ( delivery == null ) {
            return null;
        }

        DeliveryDTO.DeliveryDTOBuilder deliveryDTO = DeliveryDTO.builder();

        deliveryDTO.deliveryId( delivery.getDeliveryId() );
        deliveryDTO.deliveryNumber( delivery.getDeliveryNumber() );
        deliveryDTO.deliveryCompany( delivery.getDeliveryCompany() );

        return deliveryDTO.build();
    }

    @Override
    public Delivery toEntity(DeliveryDTO deliveryDTO) {
        if ( deliveryDTO == null ) {
            return null;
        }

        Delivery delivery = new Delivery();

        delivery.setDeliveryId( deliveryDTO.getDeliveryId() );
        delivery.setDeliveryNumber( deliveryDTO.getDeliveryNumber() );
        delivery.setDeliveryCompany( deliveryDTO.getDeliveryCompany() );

        return delivery;
    }
}
