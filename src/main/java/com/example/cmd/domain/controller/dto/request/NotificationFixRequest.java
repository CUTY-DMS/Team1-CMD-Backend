package com.example.cmd.domain.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NotificationFixRequest {

    private String title;
    private String dateTime;
    private String contents;
}
