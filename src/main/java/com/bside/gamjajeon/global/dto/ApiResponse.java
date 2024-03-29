package com.bside.gamjajeon.global.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiResponse<T> {

    private final boolean result;
    private final String message;
    private final T data;

    public ApiResponse(T data) {
        this.result = true;
        this.message = "";
        this.data = data;
    }

    public ApiResponse(T data, String message) {
        this.result = true;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> empty() {
        return new ApiResponse<>(null);
    }

    public static <T> ApiResponse<T> of(String message) {
        return new ApiResponse<>(null, message);
    }

}
