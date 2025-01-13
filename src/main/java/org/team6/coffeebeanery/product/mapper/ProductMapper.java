package org.team6.coffeebeanery.product.mapper;

import org.mapstruct.Mapper;
import org.team6.coffeebeanery.product.dto.ProductDTO;
import org.team6.coffeebeanery.product.model.Product;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    
    Product toEntity(ProductDTO productDTO);
}