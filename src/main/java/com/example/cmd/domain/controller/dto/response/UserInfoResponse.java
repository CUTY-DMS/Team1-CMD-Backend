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
<<<<<<< HEAD
=======
    private String password;
>>>>>>> main
    private Long classIdNumber;
    private Long birth;
    private String majorField;
    private String clubName;

<<<<<<< HEAD
    public UserInfoResponse(User user) {
        email = user.getEmail();
        username = user.getUsername();
        classIdNumber = user.getClassIdNumber();
        birth = user.getBirth();
        majorField = user.getMajorField();
        clubName = user.getClubName();
=======

    public UserInfoResponse(User user) {
        email = user.getEmail();
>>>>>>> main
    }
}