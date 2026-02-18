package com.study.global.exception;

import org.springframework.http.HttpStatus;

public class ProfileNotFoundException extends BusinessException {

    public ProfileNotFoundException(Long id) {
        super(
                "PROFILE_NOT_FOUND",
                "해당 프로필을 찾을 수 없습니다. (id: " + id + ")",
                HttpStatus.NOT_FOUND
        );
    }
}