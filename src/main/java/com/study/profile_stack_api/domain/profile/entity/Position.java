package com.study.profile_stack_api.domain.profile.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Position {
    // 인스턴스
    BACKEND("백엔드 개발자", "⚙️"),
    FRONTEND("프론트엔드 개발자", "🎨"),
    FULLSTACK("풀스택 개발자", "🔄"),
    MOBILE("모바일 개발자", "📱"),
    DEVOPS("DevOps 엔지니어", "🚀"),
    DATA("데이터 엔지니어", "📊"),
    AI("AI/ML 엔지니어", "🤖"),
    ETC("기타", "💻");

    // 필드
    private final String description;
    private final String icon;
}
