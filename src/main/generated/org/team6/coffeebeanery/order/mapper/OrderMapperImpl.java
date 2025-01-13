package org.team6.coffeebeanery.order.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.team6.coffeebeanery.common.model.Address;
import org.team6.coffeebeanery.order.dto.OrderDTO;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.model.OrderDetail;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-13T19:58:12+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (GraalVM Community)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDTO toDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDTO.OrderDTOBuilder orderDTO = OrderDTO.builder();

        orderDTO.address( addressToAddress( order.getAddress() ) );
        orderDTO.orderId( order.getOrderId() );
        orderDTO.customerEmail( order.getCustomerEmail() );
        orderDTO.orderCreatedAt( order.getOrderCreatedAt() );
        orderDTO.totalPrice( order.getTotalPrice() );
        orderDTO.orderStatus( order.getOrderStatus() );
        orderDTO.delivery( order.getDelivery() );
        List<OrderDetail> list = order.getOrderDetails();
        if ( list != null ) {
            orderDTO.orderDetails( new ArrayList<OrderDetail>( list ) );
        }

        return orderDTO.build();
    }

    @Override
    public Order toEntity(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        order.address( addressToAddress1( orderDTO.getAddress() ) );
        order.orderId( orderDTO.getOrderId() );
        order.customerEmail( orderDTO.getCustomerEmail() );
        order.orderCreatedAt( orderDTO.getOrderCreatedAt() );
        order.totalPrice( orderDTO.getTotalPrice() );
        order.orderStatus( orderDTO.getOrderStatus() );
        order.delivery( orderDTO.getDelivery() );
        List<OrderDetail> list = orderDTO.getOrderDetails();
        if ( list != null ) {
            order.orderDetails( new ArrayList<OrderDetail>( list ) );
        }

        return order.build();
    }

    protected Address addressToAddress(Address address) {
        if ( address == null ) {
            return null;
        }

        Address address1 = new Address();

        address1.setBaseAddress( address.getBaseAddress() );
        address1.setDetailAddress( address.getDetailAddress() );
        address1.setZipCode( address.getZipCode() );

        return address1;
    }

    protected Address addressToAddress1(Address address) {
        if ( address == null ) {
            return null;
        }

        Address address1 = new Address();

        address1.setBaseAddress( address.getBaseAddress() );
        address1.setDetailAddress( address.getDetailAddress() );
        address1.setZipCode( address.getZipCode() );

        return address1;
    }
}
