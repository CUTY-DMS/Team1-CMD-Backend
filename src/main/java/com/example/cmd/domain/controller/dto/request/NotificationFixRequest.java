package com.example.cmd.domain.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class NotificationFixRequest {

    private String title;
    private String dateTime;
    private String contents;
}
