package com.example.cmd.domain.service.exception;

import com.example.cmd.global.security.error.exception.BusinessException;
import com.example.cmd.global.security.error.exception.ErrorCode;

public class TokenInvalidException extends BusinessException {
    public static final BusinessException EXCEPTION = new TokenInvalidException();
    public TokenInvalidException(){
        super(ErrorCode.INVALID_TOKEN);
    }
}