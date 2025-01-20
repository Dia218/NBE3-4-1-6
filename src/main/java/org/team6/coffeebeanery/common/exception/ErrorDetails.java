package org.team6.coffeebeanery.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
    private int status; // 상태 코드
    private String message; // 에러 메시지
    private long timestamp; // 타임스탬프
}