package com.study.profile_stack_api.global.exception;

public class TechStackNotFoundException extends RuntimeException{
    public TechStackNotFoundException(Long id){
        super("해당 기술 스택을 찾을 수 없습니다. (id: " + id + ")");
    }
}
