package com.study.profile_stack_api.global.exception;

public class ProfileNotFoundException extends BusinessException {

    public ProfileNotFoundException(Long id) {
        super(404, "프로필을 찾을 수 없습니다. id: " + id);
    }
}