package com.example.cmd.domain.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class ScheduleWriteRequest {

    @NotNull(message = "제목을 입력하세요")
    private String title;

    @NotNull(message = "날짜를 입력하세요")
    private String date;
}
