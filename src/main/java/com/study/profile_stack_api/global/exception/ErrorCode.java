package com.study.profile_stack_api.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 공통 에러
    INTERNAL_ERROR("INTERNAL_ERROR", "서버 오류가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_INPUT("INVALID_INPUT", "입력값이 올바르지 않습니다", HttpStatus.BAD_REQUEST),
    BAD_CREDENTIALS("BAD_CREDENTIALS", "아이디 또는 비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("INVALID_TOKEN", "유효하지 않은 Refresh Token입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_USER("UNAUTHORIZED_USER", "인가 받은 사용자가 아닙니다.", HttpStatus.UNAUTHORIZED),

    // 커스텀 에러
    DUPLICATE_USERNAME("DUPLICATE_USERNAME", "이미 사용중인 아이디입니다.", HttpStatus.CONFLICT),
    DUPLICATE_EMAIL("DUPLICATE_EMAIL", "이미 사용중인 이메일입니다", HttpStatus.CONFLICT),
    PROFILE_NOT_FOUND("PROFILE_NOT_FOUND", "프로필을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    TECHSTACK_NOT_FOUND("TECHSTACK_NOT_FOUND", "기술스택을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
