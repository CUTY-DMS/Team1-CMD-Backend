package com.example.cmd.domain.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class ClassIdRequest {

    @NotNull(message = "학년을 입력하세요")
    private Long grade;

    @NotNull(message = "반을 입력하세요")
    private Long classes;

}
