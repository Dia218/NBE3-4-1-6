package org.team6.coffeebeanery.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.team6.coffeebeanery.order.dto.OrderDTO;
import org.team6.coffeebeanery.order.dto.OrderDetailDTO;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.model.OrderDetail;
import org.team6.coffeebeanery.product.mapper.ProductMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderMapper {

    @Mapping(source = "address.baseAddress", target = "baseAddress")
    @Mapping(source = "address.detailAddress", target = "detailAddress")
    @Mapping(source = "address.zipCode", target = "zipCode")
    @Mapping(source = "orderDetails", target = "orderDetails")
    @Mapping(source = "delivery.deliveryId", target = "deliveryId")
    OrderDTO toDTO(Order order);

    @Mapping(source = "baseAddress", target = "address.baseAddress")
    @Mapping(source = "detailAddress", target = "address.detailAddress")
    @Mapping(source = "zipCode", target = "address.zipCode")
    @Mapping(source = "orderDetails", target = "orderDetails")
    @Mapping(source = "deliveryId", target = "delivery.deliveryId")
    @Mapping(source = "deliveryId", target = "delivery.order.orderId")
    Order toEntity(OrderDTO orderDTO);

/*    @Mapping(source = "product.productId", target = "product.productId")
    @Mapping(source = "orderId", target = "order.orderId")
    OrderDetail orderDetailDTOToOrderDetail(OrderDetailDTO orderDetailDTO);*/

    List<OrderDetail> orderDetailDTOListToOrderDetailList(List<OrderDetailDTO> orderDetailDTOList);
}