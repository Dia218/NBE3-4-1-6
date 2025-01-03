package com.mysite.coffee.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {

    @NotEmpty(message = "제품 이름은 공백일 수 없습니다.")
    private String name;

    @NotNull(message = "제품 가격은 필수입니다.")
    private Integer price;

    @NotEmpty(message = "제품 설명은 공백일 수 없습니다.")
    private String description;

    @NotNull(message = "제품 재고는 필수입니다.")
    private Integer stock;

    private String image;
}
