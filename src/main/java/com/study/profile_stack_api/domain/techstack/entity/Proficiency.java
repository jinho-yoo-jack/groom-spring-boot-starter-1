package com.study.profile_stack_api.domain.techstack.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 숙련도 Enum
 */
@Getter
@RequiredArgsConstructor
public enum Proficiency {
    BEGINNER("🌱", "입문"),
    INTERMEDIATE("🌿", "중급"),
    ADVANCED("🌳", "고급"),
    EXPERT("🏆", "전문가");

    private final String icon;
    private final String description;
}
