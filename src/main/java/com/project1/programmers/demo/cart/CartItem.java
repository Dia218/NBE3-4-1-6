package com.project1.programmers.demo.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CartItem {
    private Long productId;
    private String productName;
    private int quantity;
    private Long price;
    private String imageUrl;
}
