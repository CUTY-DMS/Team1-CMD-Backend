package com.example.cmd.domain.controller;

import com.example.cmd.domain.controller.dto.request.LoginRequest;
import com.example.cmd.domain.controller.dto.request.SignupRequest;
import com.example.cmd.domain.controller.dto.response.UserInfoResponse;
import com.example.cmd.domain.service.UserService;
import com.example.cmd.global.security.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("signup")
    public void signup(@RequestBody SignupRequest signupRequest) {
        userService.signUp(signupRequest);
    }

    @PostMapping("login")
    public Token login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @GetMapping("myPage")
    public List<UserInfoResponse> myPage() {
        return userService.myPage();
    }

}