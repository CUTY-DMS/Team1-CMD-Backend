package com.example.cmd.domain.service.exception.user;

import com.example.cmd.global.security.error.exception.BusinessException;
import com.example.cmd.global.security.error.exception.ErrorCode;

public class EmailAlreadyExistException extends BusinessException {

    public static final BusinessException EXCEPTION = new EmailAlreadyExistException();
    public EmailAlreadyExistException() {
        super(ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
