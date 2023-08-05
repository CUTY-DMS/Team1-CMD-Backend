package com.example.cmd.domain.controller;

import com.example.cmd.domain.controller.dto.request.*;
import com.example.cmd.domain.controller.dto.response.NotificationListResponse;
import com.example.cmd.domain.controller.dto.response.ScheduleResponse;
import com.example.cmd.domain.controller.dto.response.UserInfoResponse;
import com.example.cmd.domain.service.UserService;
import com.example.cmd.domain.controller.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("signup")
    public void signup(@RequestBody @Valid UserSignupRequest signupRequest) {
        userService.userSignUp(signupRequest);
    }

    @PostMapping("login")
    public TokenResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return userService.userLogin(loginRequest);
    }

    @GetMapping("myPage")
    public UserInfoResponse myPage() {
        return userService.myPage();
    }

    @PatchMapping("infoChange")
    public void modifyUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        userService.modifyUserInfo(userInfoRequest);
    }

    @GetMapping("/allNoti")
    public List<NotificationListResponse> findNotification() {
        return userService.findNotification();
    }

    @GetMapping("/classNoti")
    public List<NotificationListResponse> findClassNotification() {
        return userService.findClassNotification();
    }

    @PatchMapping("password/change")
    public void passwordChange(@RequestBody PasswordChangeRequest passwordChangeRequest){
        userService.passwordChange(passwordChangeRequest);
    }

    @GetMapping("/schedule/{year}/{month}")
    public List<ScheduleResponse> getSchedule(@PathVariable int year, @PathVariable int month){
        return userService.getSchedule(year,month);
    }
}