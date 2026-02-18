package com.study.domain.profile.entity;

public enum Position {
    BACKEND("백엔드 개발자", "⚙️"),
    FRONTEND("프론트엔드 개발자", "🎨"),
    FULLSTACK("풀스택 개발자", "🔄"),
    MOBILE("모바일 개발자", "📱"),
    DEVOPS("DevOps 엔지니어", "🚀"),
    DATA("데이터 엔지니어", "📊"),
    AI("AI/ML 엔지니어", "🤖"),
    ETC("기타", "💻");

    private final String description;
    private final String icon;

    Position(String description, String icon) {
        this.description = description;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    // 값으로 Enum 찾기 (문자열 → Enum)
    public static Position from(String value) {
        return Position.valueOf(value.toUpperCase());
    }

}
