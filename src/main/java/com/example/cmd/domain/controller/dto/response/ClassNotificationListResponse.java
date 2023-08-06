package com.example.cmd.domain.controller.dto.response;

import com.example.cmd.domain.entity.Noti;
import com.example.cmd.domain.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClassNotificationListResponse {

    private Long id;

    private String title;

    private String dateTime;

    private String name;

    private Noti noti;

    private Long classes;

    private Long grade;
    public ClassNotificationListResponse(Notification notification) {
        id = notification.getId();
        title = notification.getTitle();
        dateTime = notification.getDateTime();
        name = notification.getAdmin().getName();
        noti = notification.getNoti();
        classes = notification.getClasses();
        grade = notification.getGrade();

    }
}
