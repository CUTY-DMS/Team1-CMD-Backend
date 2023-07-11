package com.example.cmd.domain.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor()
public class UserSignupRequest {

    private String name;
    private String email;
    private String password;
    private Long classes;
    private Long grade;
    private Long number;
    private Long birth;
    private String majorField;
    private String clubName;

}
