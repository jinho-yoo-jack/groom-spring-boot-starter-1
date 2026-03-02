package com.study.profile_stack_api.domain.techstack.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechCategory {
    LANGUAGE("프로그래밍언어", "📝"),
    FRAMEWORK("프레임워크", "🏗️"),
    DATABASE("데잍베이스", "💾"),
    DEVOPS("DevOps/인프라", "☁️"),
    TOOL("개발도구", "🔧"),
    ETC("기타", "📦");

    private final String description;
    private final String icon;
}
