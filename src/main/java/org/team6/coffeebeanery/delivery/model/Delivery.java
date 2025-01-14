package org.team6.coffeebeanery.delivery.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.team6.coffeebeanery.order.model.Order;

@Getter
@Setter
@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId; //배송 ID
    
    private String deliveryNumber; //운송장 번호

    @Column(length = 30)
    private String deliveryCompany; //택배사

    @OneToOne
    @JoinColumn(name ="order_id")
    private Order order; //주문 정보와 1:1 관계

}
