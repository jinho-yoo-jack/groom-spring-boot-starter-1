package com.study.profile_stack_api.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ErrorInfo error;
    private String resMessage;

    public static <T> ApiResponse<T> success(T data, String resMessage) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = true;
        res.resMessage = resMessage;
        res.data = data;
        res.error = null;
        return res;
    }

    public static <T> ApiResponse<T> error(String code, String errMessage, String resMessage) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = false;
        res.resMessage = resMessage;
        res.data = null;
        res.error = new ErrorInfo(code, errMessage);
        return res;
    }

    public static<T> ApiResponse<T> noDataSuccess(String resMessage) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = true;
        res.resMessage = resMessage;
        res.data = null;
        res.error = null;
        return res;
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorInfo {
        private final String code;
        private final String errMessage;
    }
}
