package org.team6.coffeebeanery.order.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.team6.coffeebeanery.common.dto.PageDTO;
import org.team6.coffeebeanery.common.model.Address;
import org.team6.coffeebeanery.order.dto.OrderDTO;
import org.team6.coffeebeanery.order.model.Order;
import org.team6.coffeebeanery.order.service.BuyerOrderService;
import org.team6.coffeebeanery.product.dto.ProductDTO;
import org.team6.coffeebeanery.product.model.Product;
import org.team6.coffeebeanery.product.service.BuyerProductService;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class BuyerOrderController {
    private final BuyerOrderService orderService;
    private final BuyerProductService productService;

    private static record EmailReqBody(
            @NotBlank(message= "이메일은 필수입니다.")
            @Email(message = "올바른 이메일 형식이 아닙니다")
            String email) {
    }

    private static record OrderCreateReqBody(
            @NotBlank(message = "이메일은 필수입니다")
            @Email(message = "올바른 이메일 형식이 아닙니다")
            String email,

            @NotBlank(message = "기본 주소는 필수입니다")
            @Size(max = 100, message = "기본 주소는 100자를 초과할 수 없습니다")
            String baseAddress,

            @NotBlank(message = "상세 주소는 필수입니다")
            @Size(max = 100, message = "상세 주소는 100자를 초과할 수 없습니다")
            String detailAddress,

            @NotBlank(message = "우편번호는 필수입니다")
            @Pattern(regexp = "\\d{5}", message = "우편번호는 5자리 숫자여야 합니다")
            String zipCode,

            @NotNull(message = "총 금액은 필수입니다")
            Long totalPrice
    ) {
        public Address toAddress() {
            return new Address(baseAddress, detailAddress, zipCode);
        }
    }

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

    // 장바구니 목록 조회 by 이메일
    @GetMapping("/list")
    public PageDTO<OrderDTO> orderList(@RequestParam("email") String customerEmail,
                                       @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<OrderDTO> paging = orderService.getListByEmail(customerEmail, page);
        return new PageDTO<OrderDTO>(paging);
    }

    // 장바구니 결제 수행
    @Transactional
    @PostMapping("")
    public ResponseEntity<String> order(@Valid @RequestBody OrderCreateReqBody reqBody,
                                        HttpSession session) {

        List<ProductDTO> cart = (List<ProductDTO>)session.getAttribute("cart");

        if(cart == null) {
            throw new IllegalStateException("장바구니가 비어있습니다.");
        }

        String email = reqBody.email();
        Address address = reqBody.toAddress();

        Order order = orderService.saveOrder(email, address, reqBody.totalPrice());
        try {
            for (ProductDTO item : cart) {
                Product product = productService.getProductById(item.getProductId());
                orderService.saveOrderDetail(item, order, product);
                productService.decreaseStock(item.getProductId(), item.getProductStock());
            }
        } catch(IllegalStateException e) {
            throw new IllegalStateException("주문 가능한 재고를 초과했습니다.");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("주문 성공");
    }
}
