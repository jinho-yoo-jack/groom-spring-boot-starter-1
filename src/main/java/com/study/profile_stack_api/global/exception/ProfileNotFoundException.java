package com.study.profile_stack_api.global.exception;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(Long profileId){
        super("해당 프로필 ID를 찾을 수 없습니다. (profileId: " + profileId + ")");
    }
}
