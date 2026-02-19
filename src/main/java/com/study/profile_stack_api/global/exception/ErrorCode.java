package com.study.profile_stack_api.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_INPUT(400, "잘못된 입력값입니다."),

    PROFILE_NOT_FOUND(404, "프로필을 찾을 수 없습니다."),
    TECH_STACK_NOT_FOUND(404, "기술 스택을 찾을 수 없습니다."),

    DUPLICATE_EMAIL(409, "이미 존재하는 이메일입니다."),

    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
