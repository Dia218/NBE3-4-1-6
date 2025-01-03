package org.dia.coffeebeanery.delivery.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer delivery_id; //배송 ID
    
    private Double delivery_number; //운송장 번호
    
    @Column(length = 30)
    private String delivery_company; //택배사
    
    @Column(length = 60)
    private String delivery_address; //배송지
}
