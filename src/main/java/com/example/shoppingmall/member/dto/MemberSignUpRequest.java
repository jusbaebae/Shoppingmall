package com.example.shoppingmall.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberSignUpRequest {

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수 항목 입니다.")
    private String email;

    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @NotBlank(message = "비밀번호는 필수 항목 입니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 항목 입니다.")
    private String confirmPassword;

    @NotBlank(message = "이름은 필수 항목 입니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수 항목 입니다.")
    private String nickname;

    @NotBlank(message = "핸드폰은 필수 항목 입니다.")
    private String phone;
}
