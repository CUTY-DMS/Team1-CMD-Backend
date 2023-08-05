package com.example.cmd.domain.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ScheduleRequest {

    private int year;

    private int month;

    private Long grade;

    private Long classes;

}
