package com.study.global.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends BusinessException {

    public DuplicateEmailException(String email) {
        super(
                "DUPLICATE_EMAIL",
                "이미 사용 중인 이메일입니다. (email: " + email + ")",
                HttpStatus.CONFLICT
        );
    }
}