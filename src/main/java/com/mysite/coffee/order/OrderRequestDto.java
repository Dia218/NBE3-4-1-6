package com.mysite.coffee.order;

import com.mysite.coffee.delivery.Delivery;
import com.mysite.coffee.orderDetail.OrderDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {

    private String email;

    private Delivery delivery;

    private Integer totalPrice;

    private List<OrderDetail> orderDetails;
}
