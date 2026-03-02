package com.study.profile_stack_api.global.exception;

public class UnauthorizedException extends BusinessException{

    public UnauthorizedException(String message) {
        super(ErrorCode.UNAUTHORIZED_USER, message);
    }
}
