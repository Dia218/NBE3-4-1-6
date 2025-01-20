package org.team6.coffeebeanery.order.dto;

import jakarta.validation.constraints.*;
import org.team6.coffeebeanery.common.model.Address;

public record OrderCreateReqBody(
        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "올바른 이메일 형식이 아닙니다")
        String email,

        @NotBlank(message = "기본 주소는 필수입니다")
        @Size(max = 100, message = "기본 주소는 100자를 초과할 수 없습니다")
        String baseAddress,

        @NotBlank(message = "상세 주소는 필수입니다")
        @Size(max = 100, message = "상세 주소는 100자를 초과할 수 없습니다")
        String detailAddress,

        @NotBlank(message = "우편번호는 필수입니다")
        @Pattern(regexp = "\\d{5}", message = "우편번호는 5자리 숫자여야 합니다")
        String zipCode,

        @PositiveOrZero (message = "가격으로 음수는 불가능합니다.")
        Long totalPrice
) {
    public Address toAddress() {
        return new Address(baseAddress, detailAddress, zipCode);
    }
}