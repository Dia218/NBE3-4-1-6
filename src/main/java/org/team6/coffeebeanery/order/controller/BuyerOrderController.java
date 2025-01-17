package org.team6.coffeebeanery.order.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.team6.coffeebeanery.common.dto.PageDTO;
import org.team6.coffeebeanery.order.dto.EmailReqBody;
import org.team6.coffeebeanery.order.dto.OrderCreateReqBody;
import org.team6.coffeebeanery.order.dto.OrderDTO;
import org.team6.coffeebeanery.order.service.BuyerOrderService;
import org.team6.coffeebeanery.product.dto.ProductDTO;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class BuyerOrderController {
    private final BuyerOrderService orderService;

    // 이메일 검증
    @PostMapping("/email")
    public ResponseEntity<Void> validateEmail(@Valid @RequestBody EmailReqBody emailReqBody) {

        if(!orderService.validateEmail(emailReqBody.email())){
            throw new IllegalArgumentException("주문 내역이 존재하지 않는 이메일입니다.");
        }

        return ResponseEntity
                .status(HttpStatus.FOUND)  // 302 상태 코드 (리다이렉트)
                .header(HttpHeaders.LOCATION, "/order/list")  // 리다이렉트할 URL
                .build();
    }

    // 주문 목록 조회 by 이메일
    @GetMapping("/list")
    public PageDTO<OrderDTO> orderList(@RequestParam("email") String customerEmail,
                                       @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<OrderDTO> paging = orderService.getListByEmail(customerEmail, page);
        return new PageDTO<>(paging);
    }

    // 장바구니 결제 수행
    @Transactional
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void order(@Valid @RequestBody OrderCreateReqBody reqBody,
                                        HttpSession session) {

         List<ProductDTO> cart = (List<ProductDTO>)session.getAttribute("cart");

/*
        프론트 테스트 용도
        List<ProductDTO> cart = new ArrayList<ProductDTO>();

        Product product1 = new Product();
        product1.setProductId(1L);
        product1.setProductStock(3);
        product1.setProductPrice(10000L);

        Product product2 = new Product();
        product2.setProductId(2L);
        product2.setProductStock(2);
        product2.setProductPrice(15000L);

        cart.add(ProductDTO.toDTO(product1));
        cart.add(ProductDTO.toDTO(product2));*/

        if(cart == null) {
            throw new IllegalStateException("장바구니가 비어있습니다.");
        }

        try {
            orderService.saveOrderWithCart(cart, reqBody);
        } catch(IllegalStateException e) {
            throw new IllegalStateException("주문 가능한 재고를 초과했습니다.");
        }
    }
}
