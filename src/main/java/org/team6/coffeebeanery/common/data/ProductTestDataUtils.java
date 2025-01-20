package org.team6.coffeebeanery.common.data;

import org.springframework.stereotype.Component;
import org.team6.coffeebeanery.product.model.Product;
import org.team6.coffeebeanery.product.repository.ProductRepository;

import java.util.Arrays;

@Component
public class ProductTestDataUtils {
    public static void createTestProducts(ProductRepository productRepository) {
        String productNameA = "커피콩 이름 A";
        String productNameB = "커피콩 이름 B";
        String productNameC = "커피콩 이름 C";
        
        String productDescriptionA = "AA산 고급 원두 입니다.";
        String productDescriptionB = "BB산 멋진 원두 입니다.";
        String productDescriptionC = "CC산 풍미가 가득한 원두 입니다.";
        
        String productImageA = "https://coffeegdero.com/web/product/big/202309/c085fd0917547dd2161e027ad3097d45.jpg";
        String productImageB = "https://coffeegdero.com/web/product/big/202309/469d46aa4f5c2b39f5fab5be7fb9219a.jpg";
        String productImageC = "https://coffeegdero.com/web/product/big/202309/33e6681c7d2e751b11eac394bac9697d.jpg";
        
        productRepository.saveAll(
                Arrays.asList(createProduct(productNameA, productDescriptionA, 10000L, productImageA, 100),
                              createProduct(productNameB, productDescriptionB, 15000L, productImageB, 200),
                              createProduct(productNameC, productDescriptionC, 12000L, productImageC, 150)));
    }
    
    public static void deleteTestProducts(ProductRepository productRepository) {
        productRepository.deleteAll();
    }
    
    private static Product createProduct(String name, String description, Long price, String imageURL, Integer stock) {
        Product product = new Product();
        product.setProductName(name);
        product.setProductDescription(description);
        product.setProductPrice(price);
        product.setProductImageURL(imageURL);
        product.setProductStock(stock);
        return product;
    }
}
