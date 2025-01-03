package org.dia.coffeebeanery.product.repository;

import org.dia.coffeebeanery.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p.product_stock FROM Product p WHERE p.product_id = :productId")
    Integer findStockByProductId(@Param("productId") Integer productId);
}
