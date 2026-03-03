package com.study.profile_stack_api.domain.profile.entity;

import lombok.Getter;

@Getter
public enum Position {
    BACKEND("백엔드 개발자"),
    FRONTEND("프론트엔드 개발자"),
    FULLSTACK("풀스택 개발자"),
    MOBILE("모바일 개발자"),
    DEVOPS("데브옵스 개발자"),
    DATA("데이터 개발자"),
    AI("AI 개발자"),
    ETC("기타");

    private final String description;

    Position(String description) {
        this.description = description;
    }
}