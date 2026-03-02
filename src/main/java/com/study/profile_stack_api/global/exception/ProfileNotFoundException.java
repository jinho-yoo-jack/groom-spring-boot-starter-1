package com.study.profile_stack_api.global.exception;

public class ProfileNotFoundException extends BusinessException{

    public ProfileNotFoundException() {
        super(ErrorCode.PROFILE_NOT_FOUND);
    }

    public ProfileNotFoundException(long id) {
        super(ErrorCode.PROFILE_NOT_FOUND, "해당 프로필을 찾을 수 없습니다. (id: " + id + ")");
    }
}
