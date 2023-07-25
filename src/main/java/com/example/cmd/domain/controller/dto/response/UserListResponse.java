package com.example.cmd.domain.controller.dto.response;

import com.example.cmd.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserListResponse {

    private String name;
    private Long grade;
    private Long classes;
    private Long number;

    public UserListResponse(User user) {
        name = user.getName();
        grade = user.getGrade();
        classes = user.getClasses();
        number = user.getNumber();
    }
}