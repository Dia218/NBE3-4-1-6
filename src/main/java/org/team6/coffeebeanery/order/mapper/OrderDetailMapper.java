package org.team6.coffeebeanery.order.mapper;

import org.mapstruct.Mapper;
import org.team6.coffeebeanery.product.mapper.ProductMapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderDetailMapper {
    /*@Mapping(source = "order.orderId", target = "orderId")
    OrderDetailDTO toDTO(OrderDetail orderDetail);*/

    // OrderDetail toEntity(OrderDetailDTO orderDetailDTO);
}