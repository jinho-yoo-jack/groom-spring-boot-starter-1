package com.study.profile_stack_api.global.exception;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String dataName, Long id) {
        super(String.format("%s 을(를) 찾을 수 없습니다: %d", dataName, id));
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
