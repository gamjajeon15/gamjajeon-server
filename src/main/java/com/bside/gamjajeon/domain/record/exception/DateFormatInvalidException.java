package com.bside.gamjajeon.domain.record.exception;

import com.bside.gamjajeon.global.dto.enums.ErrorCode;
import com.bside.gamjajeon.global.error.GeneralException;

public class DateFormatInvalidException extends GeneralException {
	public DateFormatInvalidException() {super(ErrorCode.DATE_FORMAT_INVALID);}

}
