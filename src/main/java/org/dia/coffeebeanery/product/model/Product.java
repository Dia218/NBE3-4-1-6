package org.dia.coffeebeanery.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer product_id; //상품 ID
    
    @Column(length = 30)
    private String product_name; //상품 이름
    
    private Integer product_price; //상품 가격
    
    @Column(columnDefinition = "TEXT")
    private String product_description; //상품 설명
    
    private Integer product_stock; //재고 수량
    
    private LocalDateTime product_created_at; //상품 등록일 - 필요 없을 것 같다고 생각함
}
