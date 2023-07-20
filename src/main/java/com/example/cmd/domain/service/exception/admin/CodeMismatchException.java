package com.example.cmd.domain.service.exception.admin;

import com.example.cmd.global.security.error.exception.BusinessException;
import com.example.cmd.global.security.error.exception.ErrorCode;

public class CodeMismatchException extends BusinessException {

    public static final BusinessException EXCEPTION = new CodeMismatchException();

    public CodeMismatchException() {
        super(ErrorCode.CODE_MISMATCH);
    }

}
