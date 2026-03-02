package com.study.profile_stack_api.global.exception;

public class DuplicateUserNameException extends BusinessException{
    public DuplicateUserNameException(String username) {
        super(ErrorCode.DUPLICATE_USERNAME, "이미 사용중인 아이디입니다. (username: " + username + ")");
    }
}
