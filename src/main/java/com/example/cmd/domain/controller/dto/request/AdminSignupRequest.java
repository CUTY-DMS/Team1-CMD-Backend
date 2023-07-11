package com.example.cmd.domain.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminSignupRequest {
    private String email;
    private Long teachGrade;
    private Long teachClass;
    private String name;
    private String password;
    private Long birth;
    private String code;
}
