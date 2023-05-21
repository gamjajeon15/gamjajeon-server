package com.bside.gamjajeon.global.dto;

import com.bside.gamjajeon.global.dto.enums.ErrorCode;
import com.bside.gamjajeon.global.error.GeneralException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorResponse {

    private final boolean success;
    private final String message;
    private final String errorCode;

    public ErrorResponse(String errorCode, String message) {
        this.success = false;
        this.message = message;
        this.errorCode = errorCode;
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(errorCode.getCode(), message);
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }

    public static ErrorResponse of(GeneralException e) {
        return new ErrorResponse(e.getCode(), e.getMessage());
    }
}
