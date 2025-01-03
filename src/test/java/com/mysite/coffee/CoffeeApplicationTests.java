package com.mysite.coffee;

import com.mysite.coffee.delivery.Delivery;
import com.mysite.coffee.delivery.DeliveryService;
import com.mysite.coffee.delivery.DeliveryStatus;
import com.mysite.coffee.delivery.DeliveryStatusScheduler;
import com.mysite.coffee.product.ProductRequestDto;
import com.mysite.coffee.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest()
class CoffeeApplicationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DeliveryStatusScheduler deliveryStatusScheduler;

    @Test
    void addProduct() {
        ProductRequestDto productRequestDto1 = new ProductRequestDto();
        productRequestDto1.setName("커피1");
        productRequestDto1.setPrice(5000);
        productRequestDto1.setDescription("테스트 커피1 입니다.");
        productRequestDto1.setStock(100);
        productRequestDto1.setImage("https://i.imgur.com/HKOFQYa.jpeg");
        productService.create(productRequestDto1);

        ProductRequestDto productRequestDto2 = new ProductRequestDto();
        productRequestDto2.setName("커피2");
        productRequestDto2.setPrice(5500);
        productRequestDto2.setDescription("테스트 커피2 입니다.");
        productRequestDto2.setStock(200);
        productRequestDto2.setImage("https://i.imgur.com/HKOFQYa.jpeg");
        productService.create(productRequestDto2);

        ProductRequestDto productRequestDto3 = new ProductRequestDto();
        productRequestDto3.setName("커피3");
        productRequestDto3.setPrice(6000);
        productRequestDto3.setDescription("테스트 커피3 입니다.");
        productRequestDto3.setStock(300);
        productRequestDto3.setImage("https://i.imgur.com/HKOFQYa.jpeg");
        productService.create(productRequestDto3);

        ProductRequestDto productRequestDto4 = new ProductRequestDto();
        productRequestDto4.setName("커피4");
        productRequestDto4.setPrice(6500);
        productRequestDto4.setDescription("테스트 커피4 입니다.");
        productRequestDto4.setStock(400);
        productRequestDto4.setImage("https://i.imgur.com/HKOFQYa.jpeg");
        productService.create(productRequestDto4);

        ProductRequestDto productRequestDto5 = new ProductRequestDto();
        productRequestDto5.setName("커피5");
        productRequestDto5.setPrice(3500);
        productRequestDto5.setDescription("테스트 커피5 입니다.");
        productRequestDto5.setStock(400);
        productRequestDto5.setImage("https://i.imgur.com/HKOFQYa.jpeg");
        productService.create(productRequestDto5);

        ProductRequestDto productRequestDto6 = new ProductRequestDto();
        productRequestDto6.setName("커피6");
        productRequestDto6.setPrice(3500);
        productRequestDto6.setDescription("테스트 커피6 입니다.");
        productRequestDto6.setStock(400);
        productRequestDto6.setImage("https://i.imgur.com/HKOFQYa.jpeg");
        productService.create(productRequestDto6);

    }

    @Test
    @DisplayName("스케쥴링 테스트")
    @Transactional
    public void testScheduledTask() {
        Delivery delivery = new Delivery();
        delivery.setDeliveryStatus(DeliveryStatus.PREPARING);
        deliveryService.save(delivery);

        deliveryStatusScheduler.processDeliveryUpdates();

        Delivery updateDelivery = deliveryService.findById(delivery.getId()).orElseThrow();

        assertThat(updateDelivery.getDeliveryStatus()).isEqualTo(DeliveryStatus.SHIPPED);
    }
}
