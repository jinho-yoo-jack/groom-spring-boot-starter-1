package com.study.profile_stack_api.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(200, "성공", data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<T>(201, "생성 완료", data);
    }

    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<T>(status, message, null);
    }
}
