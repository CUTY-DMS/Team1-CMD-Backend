package com.example.cmd.domain.controller.dto.response;

import com.example.cmd.domain.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationResponse {

    private String title;

    private String dateTime;

    public NotificationResponse(Notification notification) {
        title = notification.getTitle();
        dateTime = notification.getDateTime();

    }
}