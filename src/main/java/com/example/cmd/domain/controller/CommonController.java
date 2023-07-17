package com.example.cmd.domain.controller;

import com.example.cmd.domain.controller.dto.request.NotificationFindRequest;
import com.example.cmd.domain.controller.dto.request.PasswordChangeRequest;
import com.example.cmd.domain.entity.Notification;
import com.example.cmd.domain.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommonController {


    private final CommonService commonService;

    @GetMapping("noti/check")
    public Notification checkList(@RequestBody NotificationFindRequest notificationFindRequest) {
        return commonService.getNotification(notificationFindRequest);

    }



}
