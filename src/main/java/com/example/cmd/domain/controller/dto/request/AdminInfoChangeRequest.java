package com.example.cmd.domain.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminInfoChangeRequest {

    private String password;
    private String name;
    private Long teachGrade;
    private Long teachClass;
    private Long birth;

}