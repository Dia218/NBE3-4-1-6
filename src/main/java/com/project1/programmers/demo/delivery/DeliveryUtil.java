package com.project1.programmers.demo.delivery;

import java.util.Random;

public class DeliveryUtil {
    // 운송장 번호 생성 메서드
    public static String generateTrackingNumber() {
        // 12자리 숫자 생성
        Random random = new Random();
        StringBuilder trackingNumber = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            trackingNumber.append(random.nextInt(10));
        }
        return trackingNumber.toString();
    }
}
