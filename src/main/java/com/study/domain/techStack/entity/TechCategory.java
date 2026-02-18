package com.study.domain.techStack.entity;

public enum TechCategory {
    LANGUAGE("프로그래밍 언어", "📝"),
    FRAMEWORK("프레임워크", "🏗️"),
    DATABASE("데이터베이스", "💾"),
    DEVOPS("DevOps/인프라", "☁️"),
    TOOL("개발 도구", "🔧"),
    ETC("기타", "📦");

    private final String description;
    private final String icon;

    TechCategory(String description, String icon) {
        this.description = description;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    // 문자열 값으로 Enum 찾기
    public static TechCategory from(String value) {
        return TechCategory.valueOf(value.toUpperCase());
    }
}
