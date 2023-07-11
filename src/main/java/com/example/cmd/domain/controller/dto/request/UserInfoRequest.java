package com.example.cmd.domain.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoRequest {

    private String name;
    private Long classIdNumber;
    private Long birth;
    private String majorField;
    private String clubName;


}