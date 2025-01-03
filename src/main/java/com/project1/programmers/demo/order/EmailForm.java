package com.project1.programmers.demo.order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailForm {
    @NotEmpty(message="내용은 필수항목입니다.")
    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    private String email;
}
