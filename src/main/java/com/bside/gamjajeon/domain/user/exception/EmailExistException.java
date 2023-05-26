package com.bside.gamjajeon.domain.user.exception;

import com.bside.gamjajeon.global.dto.enums.ErrorCode;
import com.bside.gamjajeon.global.error.GeneralException;

public class EmailExistException extends GeneralException {

    public EmailExistException() {
        super(ErrorCode.EMAIL_ALREADY_EXIST);
    }
}
