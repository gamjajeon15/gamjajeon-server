package com.bside.gamjajeon.domain.user.exception;

import com.bside.gamjajeon.global.dto.enums.ErrorCode;
import com.bside.gamjajeon.global.error.GeneralException;

public class UserExistException extends GeneralException {

    public UserExistException() {
        super(ErrorCode.USER_ALREADY_EXIST);
    }
}
