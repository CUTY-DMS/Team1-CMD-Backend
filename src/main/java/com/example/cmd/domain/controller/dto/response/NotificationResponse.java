package com.example.cmd.domain.controller.dto.response;

import com.example.cmd.domain.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationResponse {

    private Long id;

    private String title;

    private String dateTime;

    private String name;

    public NotificationResponse(Notification notification) {
        id = notification.getId();
        title = notification.getTitle();
        dateTime = notification.getDateTime();
        name = notification.getAdmin().getName();

    }
}