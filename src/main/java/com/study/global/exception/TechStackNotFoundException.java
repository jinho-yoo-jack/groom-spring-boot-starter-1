package com.study.global.exception;

import org.springframework.http.HttpStatus;

public class TechStackNotFoundException extends BusinessException {

    public TechStackNotFoundException(Long id) {
        super(
                "TECHSTACK_NOT_FOUND",
                "해당 기술 스택을 찾을 수 없습니다. (id: " + id + ")",
                HttpStatus.NOT_FOUND
        );
    }
}