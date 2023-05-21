package com.bside.gamjajeon.global.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiResponse<T> {

    private final boolean success;
    private final String message;
    private final T data;

    public ApiResponse(T data) {
        this.success = true;
        this.message = "";
        this.data = data;
    }

    public ApiResponse(T data, String message) {
        this.success = true;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> empty() {
        return new ApiResponse<>(null);
    }
}
