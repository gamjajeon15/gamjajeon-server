package com.bside.gamjajeon.domain.user.exception;

import com.bside.gamjajeon.global.dto.enums.ErrorCode;
import com.bside.gamjajeon.global.error.GeneralException;

public class TokenInvalidException extends GeneralException {

    public TokenInvalidException() {
        super(ErrorCode.TOKEN_INVALID);
    }
}
