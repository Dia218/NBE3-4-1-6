package org.team6.coffeebeanery.order.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.team6.coffeebeanery.order.dto.OrderDetailDTO;
import org.team6.coffeebeanery.order.model.OrderDetail;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-13T17:32:12+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.1 (GraalVM Community)"
)
@Component
public class OrderDetailMapperImpl implements OrderDetailMapper {

    @Override
    public OrderDetailDTO toDTO(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }

        OrderDetailDTO.OrderDetailDTOBuilder orderDetailDTO = OrderDetailDTO.builder();

        orderDetailDTO.orderDetailId( orderDetail.getOrderDetailId() );
        orderDetailDTO.productQuantity( orderDetail.getProductQuantity() );
        orderDetailDTO.orderPrice( orderDetail.getOrderPrice() );
        orderDetailDTO.product( orderDetail.getProduct() );
        orderDetailDTO.order( orderDetail.getOrder() );

        return orderDetailDTO.build();
    }

    @Override
    public OrderDetail toEntity(OrderDetailDTO orderDetailDTO) {
        if ( orderDetailDTO == null ) {
            return null;
        }

        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setOrderDetailId( orderDetailDTO.getOrderDetailId() );
        orderDetail.setProductQuantity( orderDetailDTO.getProductQuantity() );
        orderDetail.setOrderPrice( orderDetailDTO.getOrderPrice() );
        orderDetail.setProduct( orderDetailDTO.getProduct() );
        orderDetail.setOrder( orderDetailDTO.getOrder() );

        return orderDetail;
    }
}
