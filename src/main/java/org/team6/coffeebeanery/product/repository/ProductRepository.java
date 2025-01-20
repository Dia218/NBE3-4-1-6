package org.team6.coffeebeanery.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team6.coffeebeanery.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
