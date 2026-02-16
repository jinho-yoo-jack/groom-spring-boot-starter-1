package com.study.profile_stack_api.domain.techstack.entity;

public enum Proficency {
    BEGINNER("입문", "🌱"),
    INTERMEDIATE("중급", "🌿"),
    ADVANCED("고급", "🌳"),
    EXPERT("전문가", "🏆");

    private String description;
    private String icon;

    Proficency(String description, String icon) {
        this.description = description;
        this.icon = icon;
    }
}
