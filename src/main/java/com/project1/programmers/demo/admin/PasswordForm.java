package com.project1.programmers.demo.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordForm {
    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String password;
}
