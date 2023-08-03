package com.example.cmd.domain.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor()
public class UserSignupRequest {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @Pattern(regexp = "^(?=.*[!@#$%^&*])(?=.{1,20}$).*",
            message = "비밀번호는 최대 20글자이고, 특수문자 1개 이상이 포함되어야 합니다.")
    private String password;

    @NotNull
    private Long classId;

    @NotNull
    private Long birth;

    @NotNull
    private String majorField;

    @NotNull
    private String clubName;

}
