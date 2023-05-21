package com.bside.gamjajeon.global.error;

import com.bside.gamjajeon.global.dto.enums.ErrorCode;
import lombok.Getter;

@Getter
public abstract class GeneralException extends RuntimeException {

    private final ErrorCode errorCode;

    GeneralException() {
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    protected GeneralException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getCode() {
        return errorCode.getCode();
    }

}
