package com.example.cmd.domain.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class NotificationDeleteRequest {
    private String title;
    private String dateTime;
}
