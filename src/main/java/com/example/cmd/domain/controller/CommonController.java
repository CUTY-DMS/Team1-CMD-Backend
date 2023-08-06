package com.example.cmd.domain.controller;

import com.example.cmd.domain.controller.dto.response.NotificationResponse;
import com.example.cmd.domain.controller.dto.response.TokenResponse;
import com.example.cmd.domain.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommonController {


    private final CommonService commonService;

    @GetMapping("/noti/check/{notiId}")
    public NotificationResponse checkList(@PathVariable Long notiId) {
        return commonService.getNotification(notiId);

    }

    @PostMapping("/reissue")
    public TokenResponse reissue(@RequestHeader(name = "AUTHORIZATION_HEADER") String refreshToken) {
        return commonService.reissue(refreshToken);
    }

}
