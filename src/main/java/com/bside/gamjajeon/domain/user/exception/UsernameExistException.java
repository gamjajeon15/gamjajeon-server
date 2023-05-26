package com.bside.gamjajeon.domain.user.exception;

import com.bside.gamjajeon.global.dto.enums.ErrorCode;
import com.bside.gamjajeon.global.error.GeneralException;

public class UsernameExistException extends GeneralException {

    public UsernameExistException() {
        super(ErrorCode.ID_ALREADY_EXIST);
    }
}
