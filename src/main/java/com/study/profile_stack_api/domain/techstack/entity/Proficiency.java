package com.study.profile_stack_api.domain.techstack.entity;

public enum Proficiency {
    BEGINNER("🌱", "입문"),
    INTERMEDIATE("🌿️", "중급"),
    ADVANCED("🌳", "고급"),
    EXPERT("🏆", "전문가");

    private final String icon;
    private final String description;

    Proficiency(String icon, String description) {
        this.icon = icon;
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public static Proficiency of(String proficiencyName) {
        Proficiency proficiency;
        try {
            proficiency = Proficiency.valueOf(proficiencyName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 숙련도 입니다: " + proficiencyName);
        }

        return proficiency;
    }
}
