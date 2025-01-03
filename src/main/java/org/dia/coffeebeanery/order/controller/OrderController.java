package org.dia.coffeebeanery.order.controller;

import lombok.RequiredArgsConstructor;
import org.dia.coffeebeanery.order.model.Order;
import org.dia.coffeebeanery.order.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;
    
    @GetMapping("/order/list") //구매자용) 해당 이메일 주문 목록 페이지
    public Page<Order> getOrderListForBuyer(@RequestParam("email") String email,
                                            @RequestParam(value = "page", defaultValue = "0") int page) {
        return orderService.getOrderListByEmail(email, page);
    }
    
    @PostMapping //구매자용) 주문 생성 (주문 결제 페이지 이동)
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }
    
    @PutMapping("/{orderId}") //구매자용) 주문 상태 변경 - 주문 취소
    public Order updateOrderCancel(@PathVariable Integer orderId) {
        return orderService.cancelOrder(orderId);
    }
    
    @GetMapping("/admin/orders") //판매자용) 전체 주문 목록 페이지
    public Page<Order> getOrderListForSeller(@RequestParam(value = "page", defaultValue = "0") int page) {
        return this.orderService.getAllOrderList(page);
    }
    
    @PutMapping("/admin/orders/{orderId}") //판매자용) 주문 상태 변경
    public Order updateOrderState(@PathVariable Integer orderId, @RequestBody Map<String, Object> updateFields) {
        return orderService.updateOrderState(orderId, updateFields);
    }
    
    //주문은 삭제되지 않음
    //'주문 취소' 상태로 변경만 가능
}
