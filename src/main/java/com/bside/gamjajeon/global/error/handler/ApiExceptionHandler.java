package com.bside.gamjajeon.global.error.handler;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.bside.gamjajeon.global.dto.ErrorResponse;
import com.bside.gamjajeon.global.dto.enums.ErrorCode;
import com.bside.gamjajeon.global.error.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
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
    public ResponseEntity<Object> badCredentials(BadCredentialsException e) {
        return getResponseEntity(ErrorCode.PASSWORD_INVALID, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleMethodArgumentNotValid(BindException e) {
        return getResponseEntity(ErrorCode.VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleMismatchedInput(HttpMessageNotReadableException e) {
        log.error("[HttpMessageNotReadableException] " + e.getMessage());
        return getResponseEntity(ErrorCode.VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleAmazonServiceError(AmazonServiceException e) {
        log.error("[AmazonServiceException] " + e.getErrorMessage());
        return getResponseEntity(ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleAmazonServiceError(SdkClientException e) {
        log.error("[SdkClientException] " + e.getMessage());
        return getResponseEntity(ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e) {
        log.error("[Error]: " + e.getMessage());
        return getResponseEntity(ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> getResponseEntity(ErrorCode errorCode, HttpStatus status) {
        return ResponseEntity.status(status).body(ErrorResponse.of(errorCode));
    }

}
