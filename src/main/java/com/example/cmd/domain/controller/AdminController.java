package com.example.cmd.domain.controller;

import com.example.cmd.domain.controller.dto.request.NotificationDeleteRequest;
import com.example.cmd.domain.controller.dto.request.NotificationFixRequest;
import com.example.cmd.domain.controller.dto.request.NotificationWriteRequest;
import com.example.cmd.domain.service.AdminNotificationService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminNotificationService adminNotificationService;


    @PostMapping("write")
    public void write(@RequestBody NotificationWriteRequest notificationWriteRequest) {
        adminNotificationService.write(notificationWriteRequest);
    }

    @DeleteMapping("delete")
    public void delete(@RequestBody NotificationDeleteRequest notificationDeleteRequest){
        adminNotificationService.delete(notificationDeleteRequest);
    }

@PatchMapping("fix")
    public void fix(@RequestBody NotificationFixRequest notificationFixRequest){
        adminNotificationService.fix(notificationFixRequest);
}


}


