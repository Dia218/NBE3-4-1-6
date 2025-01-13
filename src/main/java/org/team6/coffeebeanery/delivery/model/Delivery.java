package org.team6.coffeebeanery.delivery.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId; //배송 ID
    
    private Long deliveryNumber; //운송장 번호
    
    @Column(length = 30)
    private String deliveryCompany; //택배사
}
