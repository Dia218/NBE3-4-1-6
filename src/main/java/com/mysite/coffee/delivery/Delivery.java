package com.mysite.coffee.delivery;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String postcode;

    @Column(nullable = false, name = "start_time")
    private LocalDateTime startTime;

    @Column(nullable = false, name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @PrePersist
    public void prePersistTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayAt2PM = now.toLocalDate().atTime(14, 0);
        LocalDateTime tomorrowAt2PM = todayAt2PM.plusDays(1);

        // 주문 시각각에 따라 startTime 변경
        if (now.isBefore(todayAt2PM)) {
            this.startTime = todayAt2PM;
        } else {
            this.startTime = tomorrowAt2PM;
        }
        this.endTime = this.startTime.plusDays(1);
    }
}
