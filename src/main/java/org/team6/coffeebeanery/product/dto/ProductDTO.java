package org.team6.coffeebeanery.product.dto;

import lombok.Getter;
import lombok.Setter;
import org.team6.coffeebeanery.product.model.Product;

@Getter
@Setter
public class ProductDTO {
    private Long productId;
    private String productName;
    private String productDescription;
    private Long productPrice;
    private String productImageURL;
    private Integer productStock;


    public static ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setProductName(product.getProductName());
        productDTO.setProductDescription(product.getProductDescription());
        productDTO.setProductPrice(product.getProductPrice());
        productDTO.setProductImageURL(product.getProductImageURL());
        productDTO.setProductStock(product.getProductStock());
        return productDTO;
    }
}


