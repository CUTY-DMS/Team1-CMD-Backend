package com.example.cmd.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor()
public class SignupRequest {
    private String name;
    private String email;
    private String password;
}
