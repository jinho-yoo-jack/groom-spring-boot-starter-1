package com.study.profile_stack_api.domain.tech_stack.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Proficiency {
    BEGINNER("🌱", "입문"),
    INTERMEDIATE("🌿", "중급"),
    ADVANCED("🌳", "고급"),
    EXPERT("🏆", "전문가");

    private final String icon;
    private final String description;
}
