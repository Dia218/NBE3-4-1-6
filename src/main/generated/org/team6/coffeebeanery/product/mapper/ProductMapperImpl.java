package org.team6.coffeebeanery.product.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.team6.coffeebeanery.product.dto.ProductDTO;
import org.team6.coffeebeanery.product.model.Product;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-13T17:32:11+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.1 (GraalVM Community)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setProductId( product.getProductId() );
        productDTO.setProductName( product.getProductName() );
        productDTO.setProductDescription( product.getProductDescription() );
        productDTO.setProductPrice( product.getProductPrice() );
        productDTO.setProductImageURL( product.getProductImageURL() );
        productDTO.setProductStock( product.getProductStock() );

        return productDTO;
    }

    @Override
    public Product toEntity(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setProductId( productDTO.getProductId() );
        product.setProductName( productDTO.getProductName() );
        product.setProductDescription( productDTO.getProductDescription() );
        product.setProductPrice( productDTO.getProductPrice() );
        product.setProductImageURL( productDTO.getProductImageURL() );
        product.setProductStock( productDTO.getProductStock() );

        return product;
    }
}
