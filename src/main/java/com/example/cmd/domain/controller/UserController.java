package com.example.cmd.domain.controller;

import com.example.cmd.domain.controller.dto.request.LoginRequest;
import com.example.cmd.domain.controller.dto.request.PasswordChangeRequest;
import com.example.cmd.domain.controller.dto.request.UserInfoRequest;
import com.example.cmd.domain.controller.dto.request.UserSignupRequest;
import com.example.cmd.domain.controller.dto.response.NotificationListResponse;
import com.example.cmd.domain.controller.dto.response.UserInfoResponse;
import com.example.cmd.domain.service.UserService;
import com.example.cmd.domain.controller.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("signup")
    public void signup(@RequestBody UserSignupRequest signupRequest) {
        userService.userSignUp(signupRequest);
    }

    @PostMapping("login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
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
}