package com.bside.gamjajeon.global.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
    VALIDATION_ERROR("C001", ErrorCategory.CLIENT, "잘못된 입력값입니다"),
    NOT_EXIST_API("C002", ErrorCategory.CLIENT, "요청 주소가 올바르지 않습니다"),
    ACCESS_DENIED("C003", ErrorCategory.CLIENT, "접근 권한이 없습니다"),
    INTERNAL_ERROR("C004", ErrorCategory.SERVER, "서버 에러입니다"),
    MISSING_PARAM_ERROR("C005", ErrorCategory.CLIENT, "필수 파라미터를 확인해주세요"),

    // User
    EMAIL_ALREADY_EXIST("U001", ErrorCategory.CLIENT, "이미 사용 중인 이메일 주소예요"),
    ID_ALREADY_EXIST("U002", ErrorCategory.CLIENT, "사용할 수 없는 아이디예요"),
    USER_NOT_FOUND("U003", ErrorCategory.CLIENT, "존재하지 않는 아이디예요"),
    PASSWORD_INVALID("U004", ErrorCategory.CLIENT, "비밀번호가 올바르지 않아요"),
    EMAIL_NOT_FOUND("U005", ErrorCategory.CLIENT, "존재하지 않는 이메일이에요"),
    ACCOUNT_NOT_FOUND("U006", ErrorCategory.CLIENT, "존재하지 않는 계정이에요"),

    // Token
    TOKEN_INVALID("T001", ErrorCategory.CLIENT, "토큰이 유효하지 않습니다"),
    TOKEN_EXPIRED("T002", ErrorCategory.CLIENT, "토큰이 만료되었습니다"),

    // Record
    RECORD_FORMAT_INVALID("R001", ErrorCategory.CLIENT, "유효한 입력값이 아니예요."),
    DATE_FORMAT_INVALID("R002", ErrorCategory.CLIENT, "날짜 형식이 맞지 않아요.(yyyy-MM-dd)"),
    RECORD_DATE_INVALID("R003", ErrorCategory.CLIENT, "미래의 날짜는 일기를 작성할 수 없어요."),
    RECORD_NOT_FOUND("R004", ErrorCategory.CLIENT, "유효하지 않은 기록 id예요.");

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
