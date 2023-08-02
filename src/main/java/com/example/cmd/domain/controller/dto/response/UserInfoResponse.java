package com.example.cmd.domain.controller.dto.response;

import com.example.cmd.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserInfoResponse {

    private String name;
    private String email;
    private Long classId;
    private Long birth;
    private String majorField;
    private String clubName;

    public UserInfoResponse(User user) {
        email = user.getEmail();
        name = user.getName();
        classId = user.getClassId();
        birth = user.getBirth();
        majorField = user.getMajorField();
        clubName = user.getClubName();
    }
}