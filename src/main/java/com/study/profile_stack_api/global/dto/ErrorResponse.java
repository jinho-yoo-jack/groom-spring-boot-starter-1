package com.study.profile_stack_api.global.dto;

import com.study.profile_stack_api.global.exception.ErrorCode;
import lombok.Getter;

import java.util.Optional;

@Getter
public class ErrorResponse {
    private final String code;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ErrorResponse(ErrorCode errorCode, String customMessage) {
        this.code = errorCode.getCode();
        this.message = customMessage;
    }

    public static ErrorResponse of(ErrorCode errorCode, String customMessage) {
        return Optional.ofNullable(customMessage)
                .map(msg -> new ErrorResponse(errorCode, customMessage))
                .orElseGet(() -> new ErrorResponse(errorCode));
    }
}
