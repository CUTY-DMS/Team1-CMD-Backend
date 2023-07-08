package com.example.cmd;

import com.example.cmd.domain.dto.request.LoginRequest;
import com.example.cmd.domain.dto.request.SignupRequest;
import com.example.cmd.domain.service.UserService;
import com.example.cmd.global.security.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.CharacterCodingException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;


    @PostMapping("signup")
    public void signup(@RequestBody SignupRequest signupRequest) {
        userService.signUp(signupRequest);
    }

    @PostMapping("login")
    public Token login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
}

