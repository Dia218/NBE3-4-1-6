package com.mysite.coffee.product;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_price")
    private Integer price;

    @Column(name = "product_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "product_stock")
    private Integer stock;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "product_image")
    private String image;

    public Product(ProductRequestDto productRequestDto) {
        this.setName(productRequestDto.getName());
        this.setDescription(productRequestDto.getDescription());
        this.setPrice(productRequestDto.getPrice());
        this.setStock(productRequestDto.getStock());
        this.setImage(productRequestDto.getImage());
    }

    public void decreaseStock(Integer quantity) {
        if (this.stock < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stock -= quantity;
    }
}
