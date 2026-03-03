package com.study.profile_stack_api.domain.techstack.entity;

import lombok.Getter;

@Getter
public enum Category {
    LANGUAGE("프로그래밍 언어"),
    FRAMEWORK("프레임워크"),
    DATABASE("데이터베이스"),
    TOOL("개발 도구"),
    DEVOPS("데브옵스");

    private final String description;

    Category(String description) {
        this.description = description;
    }
}