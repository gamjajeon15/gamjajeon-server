package com.bside.gamjajeon.domain.record.exception;

import com.bside.gamjajeon.global.dto.enums.ErrorCode;
import com.bside.gamjajeon.global.error.GeneralException;

public class RecordNotFoundException extends GeneralException {
    public RecordNotFoundException() {
        super(ErrorCode.RECORD_NOT_FOUND);
    }
}
