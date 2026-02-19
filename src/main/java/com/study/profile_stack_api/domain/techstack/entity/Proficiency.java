package com.study.profile_stack_api.domain.techstack.entity;

public enum Proficiency {
    BEGINNER("입문", "🌱"),
    INTERMEDIATE("중급", "🌿"),
    ADVANCED("고급", "🌳"),
    EXPERT("전문가", "🏆");

    private String description;
    private String icon;

    Proficiency(String description, String icon) {
        this.description = description;
        this.icon = icon;
    }

    // Getter
    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
