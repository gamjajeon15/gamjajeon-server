package com.bside.gamjajeon.domain.user.exception;

import com.bside.gamjajeon.global.dto.enums.ErrorCode;
import com.bside.gamjajeon.global.error.GeneralException;

public class EmailNotFoundException extends GeneralException {

    public EmailNotFoundException() {
        super(ErrorCode.EMAIL_NOT_FOUND);
    }
}
