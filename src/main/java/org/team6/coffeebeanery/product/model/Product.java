package org.team6.coffeebeanery.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.team6.coffeebeanery.common.validation.Url;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId; //상품 ID
    
    @NotNull(message = "상품 이름은 필수입니다.")
    @Size(min = 1, max = 30, message = "상품 이름은 1자 이상, 30자 이하로 입력해주세요.")
    private String productName; //상품 이름
    
    @Size(max = 500, message = "상품 설명은 500자 이하로 입력해주세요.")
    private String productDescription; //상품 설명
    
    @NotNull(message = "상품 가격은 필수입니다.")
    @Min(value = 1, message = "상품 가격은 1원 이상이어야 합니다.")
    private Long productPrice; //상품 가격
    
    @Url(message = "유효한 이미지 URL을 입력해주세요.")
    private String productImageURL; //상품 이미지 URL
    
    @NotNull(message = "상품 재고는 필수입니다.")
    @Min(value = 0, message = "상품 재고는 음수일 수 없습니다.")
    private Integer productStock; //재고 수량
    
    @Column(updatable = false)
    private LocalDateTime productCreatedAt; //상품 등록일
    
    @PrePersist
    public void onPrePersist() {
        this.productCreatedAt = LocalDateTime.now(); // 상품 등록 시 자동으로 현재 시간 설정
    }
}