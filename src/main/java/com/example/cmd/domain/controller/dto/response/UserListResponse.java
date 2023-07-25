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
//리스트 정렬하기, 학번을 학년 반 번호로 쪼개서 주기, request gradeClass를 빼기