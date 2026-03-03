package com.study.profile_stack_api.global.exception;

public class ProfileNotFoundException extends BusinessException {

    public ProfileNotFoundException(Long id) {
        super(ErrorCode.PROFILE_NOT_FOUND.getStatus(), ErrorCode.PROFILE_NOT_FOUND.getMessage() + " id: " + id);
    }
}