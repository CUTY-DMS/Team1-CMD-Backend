package com.example.cmd.domain.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class PasswordChangeRequest {

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String oldPassword;

    @Pattern(regexp = "^(?=.*[!@#$%^&*])(?=.{1,20}$).*",
            message = "비밀번호는 최대 20글자이고, 특수문자 1개 이상이 포함되어야 합니다.")
    private String newPassword;

    @NotNull(message = "비밀번호를 다시 입력해주세요.")
    private String reNewPassword;

}
