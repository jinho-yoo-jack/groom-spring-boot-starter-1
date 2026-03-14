package com.study.profile_stack_api.domain.profile.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(Long id) {
        super(String.format("존재하지 않는 ID : %d", id));
    }
}
