package com.example.cmd.domain.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class AdminSignupRequest {

    @NotNull(message = "email을 입력하세요")
    private String email;

    private Long teachGrade;

    private Long teachClass;

    @NotNull(message = "이름을 입력하세요")
    private String name;

    @Pattern(regexp = "^(?=.*[!@#$%^&*])(?=.{1,20}$).*",
            message = "비밀번호는 최대 20글자이고, 특수문자 1개 이상이 포함되어야 합니다.")
    private String password;

    @NotNull(message = "생년월일을 입력하세요")
    private Long birth;

    @NotNull(message = "가입 코드를 입력하세요")
    private String code;
}
