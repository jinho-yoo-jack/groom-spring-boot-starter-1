package com.study.profile_stack_api.domain.tech_stack.entity;

public enum Proficiency {
    BEGINNER("🌱", "입문"),
    INTERMEDIATE("🌿", "중급"),
    ADVANCED("🌳", "고급"),
    EXPERT("🏆", "전문가");

    private final String icon;
    private final String description;

    Proficiency(String icon, String description) {
        this.icon = icon;
        this.description = description;
    }
}
