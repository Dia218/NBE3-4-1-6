package com.project1.programmers.demo.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm {
    @NotBlank(message="")
    private String name;

    @NotNull(message="가격 입력은 필수입니다.")
    @Min(value=0, message="가격은 0원 이상이어야 합니다.")
    private Long price;

    @NotNull(message="재고 입력은 필수입니다.")
    @Min(value=0, message="재고는 0개 이상이어야 합니다.")
    private Integer stockQuantity;

    @NotBlank(message="이미지 URL은 필수입니다.")
    private String imageUrl;

    @NotBlank(message="원산지 표시는 필수입니다.")
    private String origin;

    @NotBlank(message="상품 설명은 필수입니다.")
    private String description;
}
