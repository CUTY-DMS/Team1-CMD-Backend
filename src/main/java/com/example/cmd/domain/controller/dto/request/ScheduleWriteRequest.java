package com.example.cmd.domain.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class ScheduleWriteRequest {

    @NotNull(message = "제목을 입력하세요")
    private String title;

    @NotNull(message = "년도를 입력하세요")
    private int year;

    @NotNull(message = "달을 입력하세요")
    private int month;

    @NotNull(message = "일을 입력하세요")
    private int day;

    @NotNull(message = "학년을 입력하세요")
    private Long grade;

    @NotNull(message = "반을 입력하세요")
    private Long classes;
}
