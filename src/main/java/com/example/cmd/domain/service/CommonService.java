package com.example.cmd.domain.service;

import com.example.cmd.domain.controller.dto.request.NotificationFindRequest;
import com.example.cmd.domain.controller.dto.request.PasswordChangeRequest;
import com.example.cmd.domain.controller.dto.response.TokenResponse;
import com.example.cmd.domain.entity.Notification;
import com.example.cmd.domain.repository.NotificationRepository;
import com.example.cmd.domain.repository.UserRepository;
import com.example.cmd.domain.service.exception.notification.NotificationNotFoundException;
import com.example.cmd.global.security.auth.AuthDetailsService;
import com.example.cmd.global.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public Notification getNotification(NotificationFindRequest notificationFindRequest) {
        return notificationRepository.findById(notificationFindRequest.getId())
                .orElseThrow(() -> NotificationNotFoundException.EXCEPTION);
    }

    @Transactional
    public TokenResponse reissue(String refreshToken) {
        System.out.println("pelassssse");
        return jwtTokenProvider.reissue(refreshToken);
    }

}
