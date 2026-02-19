package com.study.profile_stack_api.global.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email){
        super("이미 사용중인 이메일 입니다. (email: " + email + ")");
    }

}
