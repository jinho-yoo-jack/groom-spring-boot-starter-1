package com.study.profile_stack_api.global.exception;

public class DuplicateEmailException extends BusinessException {

    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL);
    }

    public DuplicateEmailException(String email) {
        super(ErrorCode.DUPLICATE_EMAIL, "이미 사용중인 이메일입니다. (email: " + email + ")");
    }
}
