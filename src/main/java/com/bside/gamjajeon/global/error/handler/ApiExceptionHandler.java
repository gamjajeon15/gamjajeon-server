package com.bside.gamjajeon.global.error.handler;

import com.bside.gamjajeon.global.dto.ErrorResponse;
import com.bside.gamjajeon.global.dto.enums.ErrorCode;
import com.bside.gamjajeon.global.error.GeneralException;
import com.bside.gamjajeon.domain.user.exception.UserExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e) {
        HttpStatus status = e.getErrorCode().isServerSideError()
                ? HttpStatus.INTERNAL_SERVER_ERROR
                : HttpStatus.BAD_REQUEST;

        return getResponseEntity(e.getErrorCode(), status);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleMethodArgumentNotValid(BindException e) {
        return getResponseEntity(ErrorCode.VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> userExist(UserExistException e) {
        return getResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e) {
        return getResponseEntity(ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> getResponseEntity(GeneralException e, HttpStatus status) {
        return ResponseEntity.status(status).body(ErrorResponse.of(e));
    }

    private ResponseEntity<Object> getResponseEntity(ErrorCode errorCode, HttpStatus status) {
        return ResponseEntity.status(status).body(ErrorResponse.of(errorCode));
    }

}
