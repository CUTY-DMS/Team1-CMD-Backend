package com.example.cmd.global.security.exception;

import com.example.cmd.global.security.error.exception.BusinessException;
import com.example.cmd.global.security.error.exception.ErrorCode;

public class ExpiredTokenException extends BusinessException {
    public static final BusinessException EXCEPTION = new ExpiredTokenException();
    public ExpiredTokenException(){
        super(ErrorCode.EXPIRED_TOKEN);
    }
}