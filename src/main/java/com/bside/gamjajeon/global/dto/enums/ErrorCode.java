package com.bside.gamjajeon.global.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
    VALIDATION_ERROR("C001", ErrorCategory.CLIENT, "잘못된 입력값입니다."),
    NOT_EXIST_API("C002", ErrorCategory.CLIENT, "요청 주소가 올바르지 않습니다."),
    ACCESS_DENIED("C003", ErrorCategory.CLIENT, "접근 권한이 없습니다."),
    INTERNAL_ERROR("C004", ErrorCategory.SERVER, "서버 에러입니다."),

    // User
    USER_ALREADY_EXIST("U001", ErrorCategory.CLIENT, "이미 가입된 계정입니다");

    private final String code;
    private final ErrorCategory errorCategory;
    private final String message;

    public enum ErrorCategory {
        NORMAL, CLIENT, SERVER
    }

    /**
     * 메세지를 직접 입력할 때 사용
     *
     * @param message 직접 입력할 메세지
     * @return 메세지
     */
    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(getMessage());
    }

    /**
     * 예외를 받아 예외 메세지를 가져올 때 사용
     *
     * @param e 예외 클래스
     * @return 예외 메세지
     */
    public String getMessage(Exception e) {
        return getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public boolean isServerSideError() {
        return this.getErrorCategory() == ErrorCategory.SERVER;
    }

}
