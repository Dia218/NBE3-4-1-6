package org.team6.coffeebeanery.global.initData;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import org.team6.coffeebeanery.common.constant.OrderStatus;
import org.team6.coffeebeanery.common.model.Address;
import org.team6.coffeebeanery.delivery.model.Delivery;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.model.OrderDetail;
import org.team6.coffeebeanery.order.repository.OrderRepository;
import org.team6.coffeebeanery.order.service.SellerOrderService;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final SellerOrderService sellerOrderService;
    private final OrderRepository orderRepository;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work();
        };
    }

    @Transactional
    public void work() {
        if (sellerOrderService.count() > 0 ) return;
        for (int i = 1; i <= 17; i++) {
            Delivery delivery = new Delivery();
            delivery.setDeliveryNumber((double) i * 1000);
            delivery.setDeliveryCompany("우체국");

            List<OrderDetail> orderDetails = new ArrayList<>();


            Order order = Order
                    .builder()
                    .customerEmail("email%d@email.com".formatted(i))
                    .address(new Address("baseAddress" + i,
                            "detailAddress" + i,
                            "z" + (int) i))
                    .totalPrice((double) i * 10000)
                    .orderStatus(OrderStatus.ORDERED)
                    .delivery(delivery)
                    .orderDetails(orderDetails)
                    .build();

            orderRepository.save(order);
        }

    }
}
