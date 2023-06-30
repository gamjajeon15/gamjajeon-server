package com.bside.gamjajeon.domain.record.exception;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bside.gamjajeon.domain.record.api.RecordController;
import com.bside.gamjajeon.global.dto.ErrorResponse;
import com.bside.gamjajeon.global.dto.enums.ErrorCode;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(assignableTypes = {RecordController.class})
public class RecordExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> makeErrorRecordFormatException(MethodArgumentNotValidException e) {
		List<FieldError> allErrors = e.getBindingResult().getFieldErrors();
		StringBuilder errorMessage = new StringBuilder("");
		for (FieldError error : allErrors) {
			errorMessage.append(error.getDefaultMessage());
		}
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.RECORD_FORMAT_INVALID, errorMessage.toString());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ParseException.class, InvalidFormatException.class, JsonParseException.class})
	public ResponseEntity<ErrorResponse> makeDateFormatException(Exception e) {
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.DATE_FORMAT_INVALID);
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
