package com.example.cmd.global.security.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    //jwt
    EXPIRED_TOKEN(401 , "Expired token"),
    INVALID_TOKEN(401, "Invalid token"),

    //user
    USER_NOT_FOUND(404,"User not found"),
    USER_ALREADY_EXISTS(409,"User already exists"),
    EMAIL_ALREADY_EXISTS(409,"Email already exists"),

    // admin
    ADMIN_NOT_FOUND(404, "Admin not found"),
    PASSWORD_MISMATCH(404, "Password Mismatch"),
    CODE_MISMATCH(404,"Code Mismatch"),


    //notification
    NOTIFICATION_NOT_FOUND(404, "Notification not found"),


    //general
    BAD_REQUEST(400, "Bad request"),
    //잘못된 요청으로써 문법상 오류가 있어서 서버가 요청사항을 이해하지 못하는 경우
    INTERNAL_SERVER_ERROR(500, "Internal server error");
    //서버 내부 오류는 웹 서버가 요청사항을 수행할 수 없을 경우에 발생함

    private final int statusCode;
    private final String message;

}
