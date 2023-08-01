package com.example.cmd.domain.service.exception;

import com.example.cmd.global.security.error.exception.BusinessException;
import com.example.cmd.global.security.error.exception.ErrorCode;

public class TokenExpiredException extends BusinessException {
    public static final BusinessException EXCEPTION = new TokenExpiredException();
    public TokenExpiredException(){
        super(ErrorCode.EXPIRED_TOKEN);
    }
}