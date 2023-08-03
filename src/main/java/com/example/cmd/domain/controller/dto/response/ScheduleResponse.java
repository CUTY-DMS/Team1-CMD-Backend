package com.example.cmd.domain.controller.dto.response;

import com.example.cmd.domain.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class ScheduleResponse {

    private Long id;

    private String title;

    private String date;

    public ScheduleResponse(Schedule schedule) {
        id = schedule.getId();
        title = schedule.getTitle();
        date = schedule.getDate();
    }
}
