package com.example.cmd.domain.service;

import com.example.cmd.domain.controller.dto.response.NotificationResponse;
import com.example.cmd.domain.controller.dto.response.TokenResponse;
import com.example.cmd.domain.entity.Notification;
import com.example.cmd.domain.repository.NotificationRepository;
import com.example.cmd.domain.repository.RefreshTokenRepository;
import com.example.cmd.domain.repository.UserRepository;
import com.example.cmd.domain.service.exception.user.UserNotFoundException;
import com.example.cmd.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;


    public NotificationResponse getNotification(Long notiId) {

        Notification notification = notificationRepository.findById(notiId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        return new NotificationResponse(notification);
        //return notificationRepository.findById(notificationFindRequest.getId())
          //      .orElseThrow(() -> NotificationNotFoundException.EXCEPTION);
    }


    @Transactional
    public TokenResponse reissue(String refreshToken) {
        return jwtTokenProvider.reissue(refreshToken);
    }

}
