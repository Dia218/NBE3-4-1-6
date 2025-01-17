package org.team6.coffeebeanery.order.converter;

import org.team6.coffeebeanery.common.constant.OrderStatus;
import org.team6.coffeebeanery.common.model.Address;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.model.OrderDetail;
import org.team6.coffeebeanery.product.dto.ProductDTO;
import org.team6.coffeebeanery.product.model.Product;

public class OrderConverter {
    private OrderConverter(){
    }

    public static Order toOrder(String email, Address address, long totalPrice ) {
        return Order.builder()
                .customerEmail(email)
                .address(address)
                .totalPrice(totalPrice)
                .orderStatus(OrderStatus.ORDERED)
                .build();
    }

    public static OrderDetail toOrderDetail(ProductDTO item, Order order, Product product) {
        return OrderDetail.builder()
                .order(order)
                .product(product)
                .orderPrice(item.getProductPrice())
                .productQuantity(item.getProductStock())
                .build();
    }
}
