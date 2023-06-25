package com.bside.gamjajeon.domain.record.exception;

import com.bside.gamjajeon.global.dto.enums.ErrorCode;
import com.bside.gamjajeon.global.error.GeneralException;

public class RecordDateInvalidException extends GeneralException {
	public RecordDateInvalidException() {super(ErrorCode.RECORD_DATE_INVALID);}
}
