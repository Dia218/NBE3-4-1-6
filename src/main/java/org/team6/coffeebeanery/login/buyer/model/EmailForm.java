package org.team6.coffeebeanery.login.buyer.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailForm {
    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String email;
}
