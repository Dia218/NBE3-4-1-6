package com.mysite.coffee.orderDetail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> save(List<OrderDetail> orderDetails) {
        return orderDetailRepository.saveAll(orderDetails);
    }

    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
}
