package com.example.cmd.domain.controller;

import com.example.cmd.domain.controller.dto.request.NotificationDeleteRequest;
import com.example.cmd.domain.controller.dto.request.NotificationWriteRequest;
import com.example.cmd.domain.controller.dto.request.SignupRequest;
import com.example.cmd.domain.entity.Role;
import com.example.cmd.domain.entity.User;
import com.example.cmd.domain.repository.UserRepository;
import com.example.cmd.domain.service.AdminNotificationService;
import com.example.cmd.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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




}


