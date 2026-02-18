package com.study.domain.techStack.entity;

public enum Proficiency {
    BEGINNER("입문", "🌱"),
    INTERMEDIATE("중급", "🌿"),
    ADVANCED("고급", "🌳"),
    EXPERT("전문가", "🏆");

    private final String description;
    private final String icon;

    Proficiency(String description, String icon) {
        this.description = description;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    // 문자열 → Enum 변환
    public static Proficiency from(String value) {
        return Proficiency.valueOf(value.toUpperCase());
    }
}
