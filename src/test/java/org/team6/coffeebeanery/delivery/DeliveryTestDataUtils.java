package org.team6.coffeebeanery.delivery;

import org.springframework.stereotype.Component;
import org.team6.coffeebeanery.delivery.model.Delivery;
import org.team6.coffeebeanery.delivery.repository.DeliveryRepository;

import java.util.Arrays;

@Component
public class DeliveryTestDataUtils {
    public static void createTestDeliveries(DeliveryRepository deliveryRepository) {
        String companyA = "대운택배";
        String companyB = "한잔택배";
        String companyC = "CC택배";

        String numberA = "1234567890";
        String numberB = "9876543210";
        String numberC = "1357924680";
        
        deliveryRepository.saveAll(Arrays.asList(createDelivery(numberA, companyA), createDelivery(numberB, companyB),
                                                 createDelivery(numberC, companyC)));
    }
    
    public static void deleteTestDeliveries(DeliveryRepository deliveryRepository) {
        deliveryRepository.deleteAll();
    }
    
    private static Delivery createDelivery(String deliveryNumber, String company) {
        Delivery delivery = new Delivery();
        delivery.setDeliveryNumber(deliveryNumber);
        delivery.setDeliveryCompany(company);
        return delivery;
    }
}
