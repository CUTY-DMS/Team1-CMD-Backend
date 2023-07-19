package com.example.cmd.domain.service.exception.admin;

import com.example.cmd.global.security.error.exception.BusinessException;
import com.example.cmd.global.security.error.exception.ErrorCode;

public class AdminNotFoundException extends BusinessException {

    public static final BusinessException EXCEPTION = new AdminNotFoundException();

    public AdminNotFoundException() {
        super(ErrorCode.ADMIN_NOT_FOUND);
    }
}
