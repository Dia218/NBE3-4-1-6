package org.team6.coffeebeanery.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.team6.coffeebeanery.order.dto.OrderDTO;
import org.team6.coffeebeanery.order.model.Order;


@Mapper(componentModel = "spring")
public interface OrderMapper {
    
    @Mapping(source = "address.baseAddress", target = "address.baseAddress")
    @Mapping(source = "address.detailAddress", target = "address.detailAddress")
    @Mapping(source = "address.zipCode", target = "address.zipCode")
    OrderDTO toDTO(Order order);
    
    @Mapping(source = "address.baseAddress", target = "address.baseAddress")
    @Mapping(source = "address.detailAddress", target = "address.detailAddress")
    @Mapping(source = "address.zipCode", target = "address.zipCode")
    Order toEntity(OrderDTO orderDTO);
}