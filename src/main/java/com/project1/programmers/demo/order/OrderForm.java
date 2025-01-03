package com.project1.programmers.demo.order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm {
    @NotBlank(message="이메일은 필수항목입니다.")
    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    String email;

    @NotBlank(message="주소는 필수항목입니다.")
    String baseAddress;

    @NotBlank(message="상세 주소는 필수항목입니다.")
    String detailAddress;

    @NotBlank(message="우편 번호는 필수항목입니다.")
    String zipCode;
}
