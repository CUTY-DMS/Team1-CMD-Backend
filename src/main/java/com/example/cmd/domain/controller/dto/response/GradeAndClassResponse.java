package com.example.cmd.domain.controller.dto.response;

import com.example.cmd.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GradeAndClassResponse {

    private Long grade;

    private Long classes;

    public GradeAndClassResponse(User user) {
        grade = user.getGrade();
        classes = user.getClasses();
    }
}
