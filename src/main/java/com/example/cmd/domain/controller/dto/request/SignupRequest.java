package com.example.cmd.domain.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor()
public class SignupRequest {

    private String name;
    private String email;
    private String password;
    private Long classIdNumber;
    private Long birth;
    private String majorField;
    private String clubName;

}
