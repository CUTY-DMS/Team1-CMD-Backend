package com.example.cmd.domain.service.exception.user;

import com.example.cmd.global.security.error.exception.BusinessException;
import com.example.cmd.global.security.error.exception.ErrorCode;

public class UserAlreadyExistException extends BusinessException {
    public static final BusinessException EXCEPTION = new UserAlreadyExistException();
    public UserAlreadyExistException() {
        super(ErrorCode.USER_ALREADY_EXISTS);
    }
}
