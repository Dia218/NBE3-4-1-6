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
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                // items 필드에 10개의 주문이 포함되어야 한다.
                .andExpect(jsonPath("$.items", Matchers.hasSize(10)))
                // 전체 주문 수는 17개
                .andExpect(jsonPath("$.totalItems").value(17))
                // 총 페이지 수는 2페이지
                .andExpect(jsonPath("$.totalPages").value(2))
                // 현재 페이지 번호는 1 (0-based index)
                .andExpect(jsonPath("$.currentPageNumber").value(1))
                // 페이지 크기는 10
                .andExpect(jsonPath("$.pageSize").value(10));
    }

    @Test
    @DisplayName("email로 주문 목록 가져오기")
    void getOrdersByEmail() throws Exception {
        List<Order> orders = createOrders();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/admin/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", "email3@email.com")
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", Matchers.hasSize(1)))  // 이메일에 해당하는 주문 1개가 첫 페이지에 있어야 함
                .andExpect(jsonPath("$.totalItems").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    @DisplayName("잘못된 email 조회시 0개")
    void getOrdersByWrongEmail() throws Exception {
        List<Order> orders = createOrders();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/admin/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", "email99@email.com")
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", Matchers.hasSize(0)))
                .andExpect(jsonPath("$.totalItems").value(0))
                .andExpect(jsonPath("$.totalPages").value(0));
    }

    // 테스트 데이터 5개
    public List<Order> createOrders() throws InterruptedException {
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
        return orderRepository.findAll();
    }


}