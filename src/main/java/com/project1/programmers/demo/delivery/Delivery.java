package com.project1.programmers.demo.delivery;

import global.jpa.entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Delivery extends BaseTime {

    @Column(nullable = false)
    private String trackingNumber;

    @Column(nullable = false)
    private String carrier;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @PrePersist
    public void prePersist() {
        this.status = DeliveryStatus.SHIPPING;  // 배송 상태를 '준비중'으로 초기화
    }
}
