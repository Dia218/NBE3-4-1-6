package org.team6.coffeebeanery.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long productId;
    private String productName;
    private String productDescription;
    private Long productPrice;
    private String productImageURL;
    private Integer productStock;
}