package org.team6.coffeebeanery.order.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.team6.coffeebeanery.order.dto.OrderDTO;
import org.team6.coffeebeanery.order.service.BuyerOrderService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BuyerOrderControllerTest {
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private BuyerOrderService orderService;
    
    @Test
    @DisplayName("주문 조회 테스트 - 정상 email")
    void t1() throws Exception {
        String email = "sample1@example.com";
        
        ResultActions resultActions = mvc.perform(get("/order/list?email=" + email))
                                         .andDo(print());
        
        Page<OrderDTO> order = orderService.getListByEmail(email, 0);
        
        resultActions.andExpect(handler().handlerType(BuyerOrderController.class))
                     .andExpect(handler().methodName("orderList"))
                     .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("이메일 검증 테스트 - 정상 email")
    void t2() throws Exception {
        String email = "sample1@example.com";
        
        ResultActions resultActions = mvc.perform(post("/order/email").content("{\"email\": \"%s\"}".formatted(email))
                                                                      .contentType(MediaType.APPLICATION_JSON))
                                         .andDo(print());
        
        resultActions.andExpect(handler().handlerType(BuyerOrderController.class))
                     .andExpect(handler().methodName("validateEmail"))
                     .andExpect(status().isFound());
    }
    
    @Test
    @DisplayName("이메일 검증 테스트 - 비정상 email")
    void t3() throws Exception {
        String email = "sample500@example.com";
        
        ResultActions resultActions = mvc.perform(post("/order/email").content("{\"email\": \"%s\"}".formatted(email))
                                                                      .contentType(MediaType.APPLICATION_JSON))
                                         .andDo(print());
        
        resultActions.andExpect(handler().handlerType(BuyerOrderController.class))
                     .andExpect(handler().methodName("validateEmail"))
                     .andExpect(status().isBadRequest())
                     .andExpect(content().string("주문 내역이 존재하지 않는 이메일입니다."));
    }
    
    @Test
    @DisplayName("주문 저장 테스트 - 빈 장바구니")
    void t4() throws Exception {
        
        ResultActions resultActions = mvc.perform(post("/order").content("""
                                                                             {
                                                                             "email": "sample100@example.com",
                                                                             "totalPrice": 1000,
                                                                             "baseAddress": "base100",
                                                                             "detailAddress": "detail100",
                                                                             "zipCode": "10000"
                                                                             }
                                                                         """.stripIndent()
                                                                            .trim())
                                                                .contentType(MediaType.APPLICATION_JSON))
                                         .andDo(print());
        
        resultActions.andExpect(handler().handlerType(BuyerOrderController.class))
                     .andExpect(handler().methodName("order"))
                     .andExpect(status().isBadRequest())
                     .andExpect(content().string("장바구니가 비어있습니다."));
    }
    
    @Test
    @DisplayName("주문 저장 테스트 - 비정상 데이터 (우편번호)")
    void t5() throws Exception {
        
        ResultActions resultActions = mvc.perform(post("/order").content("""
                                                                             {
                                                                             "email": "sample100@example.com",
                                                                             "totalPrice": 1000,
                                                                             "baseAddress": "base100",
                                                                             "detailAddress": "detail100",
                                                                             "zipCode": "100"
                                                                             }
                                                                         """.stripIndent()
                                                                            .trim())
                                                                .contentType(MediaType.APPLICATION_JSON))
                                         .andDo(print());
        
        resultActions.andExpect(handler().handlerType(BuyerOrderController.class))
                     .andExpect(handler().methodName("order"))
                     .andExpect(status().isBadRequest());
    }
}

