package com.example.cmd.domain.service;


import com.example.cmd.domain.controller.dto.request.NotificationDeleteRequest;
import com.example.cmd.domain.controller.dto.request.NotificationWriteRequest;
import com.example.cmd.domain.controller.dto.request.SignupRequest;
import com.example.cmd.domain.entity.Notification;
import com.example.cmd.domain.entity.User;
import com.example.cmd.domain.repository.NotificationRepository;
import com.example.cmd.domain.repository.UserRepository;
import com.example.cmd.domain.service.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminNotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public void write(NotificationWriteRequest notificationWriteRequest) {
        User currentUser = userFacade.getCurrentUser();

        Optional<User> userOptional = userRepository.findByEmail(currentUser.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            notificationRepository.save(
                    Notification.builder()
                            .title(notificationWriteRequest.getTitle())
                            .contents(notificationWriteRequest.getContents())
                            .user(user)
                            .dateTime(LocalDateTime.now())
                            .build()
            );
        } else {
            throw new NoSuchElementException("사용자를 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void delete(NotificationDeleteRequest notificationDeleteRequest) {
        User currentUser = userFacade.getCurrentUser();
        Optional<Notification> optionalNotification = notificationRepository.findByUserAndDateTime(currentUser, notificationDeleteRequest.getDateTime());
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notificationRepository.deleteById(notification.getId());

        } else {
           throw new NoSuchElementException("사용자 혹은 시간이 맞지 않습니다.");
        }
    }
}
