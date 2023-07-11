package com.example.cmd.domain.service;

import com.example.cmd.domain.controller.dto.request.NotificationFindRequest;
import com.example.cmd.domain.entity.Notification;
import com.example.cmd.domain.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CommonService {

    private final NotificationRepository notificationRepository;

    public Notification getNotification(NotificationFindRequest notificationFindRequest) {
        return notificationRepository.findById(notificationFindRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("no find  id"));
    }
}
