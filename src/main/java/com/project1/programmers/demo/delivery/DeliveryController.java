package com.project1.programmers.demo.delivery;

import com.project1.programmers.demo.order.Order;
import com.project1.programmers.demo.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public String getDeliveryInfo(@PathVariable("orderId") int orderId,
                                  Model model) {
        Order order = orderService.getOrderById(orderId);
        Delivery delivery = order.getDelivery();

        if(delivery == null) {
            return "delivery_info";
        }

        model.addAttribute("order", order);
        model.addAttribute("delivery", delivery);
        return "delivery_info";
    }

}
