package com.example.cmd.domain.service.exception.admin;

import com.example.cmd.global.security.error.exception.BusinessException;
import com.example.cmd.global.security.error.exception.ErrorCode;

public class PasswordMismatch extends BusinessException {

    public static final BusinessException EXCEPTION = new PasswordMismatch();

    public PasswordMismatch() {
        super(ErrorCode.PASSWORD_MISMATCH);
    }
}
