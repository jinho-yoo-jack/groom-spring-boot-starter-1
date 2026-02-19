package com.study.profile_stack_api.global.exception;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String email) {
        super(String.format("이미 존재하는 이메일입니다: %s", email));
    }
}
