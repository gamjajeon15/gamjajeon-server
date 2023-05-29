package com.bside.gamjajeon.domain.user.exception;

import com.bside.gamjajeon.global.dto.enums.ErrorCode;
import com.bside.gamjajeon.global.error.GeneralException;

public class UserNotFoundException extends GeneralException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
