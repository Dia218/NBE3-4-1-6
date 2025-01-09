package org.team6.coffeebeanery.order.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class SellerOrderControllerTest {

    @Autowired
    private SellerOrderService sellerOrderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("주문 목록 가져오기")
    void getOrders() throws Exception {
        List<Order> orders = createOrders();
        System.out.println(orderRepository.findAll().size());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/admin/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerEmail").value("email5@email.com"))
                .andExpect(jsonPath("$", Matchers.hasSize(5)));
    }

    @Test
    @DisplayName("email로 주문 목록 가져오기")
    void getOrdersByEmail() throws Exception {
        List<Order> orders = createOrders();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/admin/orders/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", "email3@email.com"))
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.customerEmail == 'email3@email.com')]", hasSize(1))); // 해당 이메일 주문 개수 확인
    }

    @Test
    @DisplayName("잘못된 email 조회시 0개")
    void getOrdersByWrongEmail() throws Exception {
        List<Order> orders = createOrders();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/admin/orders/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", "email99@email.com"))
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0))); // 해당 이메일 주문 개수 확인
    }

    // 테스트 데이터 5개
    public List<Order> createOrders() throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
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
        return orderRepository.findAll();
    }


}