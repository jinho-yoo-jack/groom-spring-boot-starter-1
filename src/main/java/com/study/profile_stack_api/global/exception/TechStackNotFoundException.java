package com.study.profile_stack_api.global.exception;

public class TechStackNotFoundException extends BusinessException {
    public TechStackNotFoundException(Long id) {
        super(
                ErrorCode.TECH_STACK_NOT_FOUND,
                "해당 기술 스택을 찾을 수 없습니다. (id: " + id + ")"
        );
    }
}
