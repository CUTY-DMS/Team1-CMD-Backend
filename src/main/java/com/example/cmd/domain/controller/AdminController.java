package com.example.cmd.domain.controller;

import com.example.cmd.domain.controller.dto.request.*;
import com.example.cmd.domain.service.AdminService;
import com.example.cmd.global.security.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("signup")
    public void signup(@RequestBody AdminSignupRequest adminSignupRequest) {
        adminService.adminSignup(adminSignupRequest);
    }

    @PostMapping("login")
    public Token login(@RequestBody LoginRequest loginRequest){
        return adminService.adminLogin(loginRequest);
    }
    @PostMapping("write")
    public void write(@RequestBody NotificationWriteRequest notificationWriteRequest) {
        adminService.write(notificationWriteRequest);
    }

    @DeleteMapping("delete")
    public void delete(@RequestBody NotificationDeleteRequest notificationDeleteRequest) {
        adminService.delete(notificationDeleteRequest);
    }

    @PatchMapping("fix")
    public void fix(@RequestBody NotificationFixRequest notificationFixRequest) {
        adminService.fix(notificationFixRequest);
    }


}


