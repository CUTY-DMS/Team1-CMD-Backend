package com.example.cmd.domain.controller.dto.response;

import com.example.cmd.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserInfoResponse {

    private String username;
    private String email;
    private String password;
    private Long classIdNumber;
    private Long birth;
    private String majorField;
    private String clubName;


    public UserInfoResponse(User user) {
        email = user.getEmail();
    }
}