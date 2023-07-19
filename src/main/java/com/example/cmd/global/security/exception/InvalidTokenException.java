package com.example.cmd.global.security.exception;

import com.example.cmd.global.security.error.exception.BusinessException;
import com.example.cmd.global.security.error.exception.ErrorCode;

public class InvalidTokenException extends BusinessException {
    public static final BusinessException EXCEPTION = new InvalidTokenException();
    //
    public InvalidTokenException(){
        super(ErrorCode.INVALID_TOKEN);

    }
}
