package org.team6.coffeebeanery.global;

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
import org.team6.coffeebeanery.delivery.repository.DeliveryRepository;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.model.OrderDetail;
import org.team6.coffeebeanery.order.repository.OrderDetailRepository;
import org.team6.coffeebeanery.order.repository.OrderRepository;
import org.team6.coffeebeanery.product.model.Product;
import org.team6.coffeebeanery.product.repository.ProductRepository;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if (orderRepository.count() == 0) {
            return;
        }
        Address address = new Address();
        address.setBaseAddress("인천시");
        address.setDetailAddress("연수구");
        address.setZipCode("12345");

        Product product = new Product();
        product.setProductName("커피콩");
        product.setProductDescription("커피 입니다.");
        product.setProductPrice(5000L);
        product.setProductImageURL("https://i.imgur.com/HKOFQYa.jpeg");
        product.setProductStock(100);

        productRepository.save(product);


        for (int i = 0; i < 30; i++) {


            Order order = Order
                    .builder()
                    .customerEmail("customer%d@email.com".formatted(i))
                    .address(address)
                    .totalPrice(0L)
                    .orderStatus(OrderStatus.ORDERED)
                    .build();

            OrderDetail orderDetail = OrderDetail
                    .builder()
                    .productQuantity(2)
                    .orderPrice(product.getProductPrice())
                    .product(product)
                    .order(order)
                    .build();


            order.getOrderDetails().add(orderDetail);
            orderDetail.setOrder(order);

            Delivery delivery = new Delivery();
            delivery.setDeliveryNumber("1234567890");
            delivery.setDeliveryCompany("한진택배");
            delivery.setOrder(order);


            order.setDelivery(delivery);

            order.setTotalPrice(orderDetail.getOrderPrice() * orderDetail.getProductQuantity());

            orderRepository.save(order);
        }

    }
}
