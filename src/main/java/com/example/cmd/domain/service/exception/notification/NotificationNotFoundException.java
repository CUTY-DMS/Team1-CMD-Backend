package com.example.cmd.domain.service.exception.notification;

import com.example.cmd.global.security.error.exception.BusinessException;
import com.example.cmd.global.security.error.exception.ErrorCode;

public class NotificationNotFoundException extends BusinessException {

    public static final BusinessException EXCEPTION = new NotificationNotFoundException();

    public NotificationNotFoundException() {
        super(ErrorCode.NOTIFICATION_NOT_FOUND);
    }
}
