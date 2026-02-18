package com.study.profile_stack_api.domain.techstack.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Proficiency {
    // 인스턴스
    BIGINNER("입문", "🌱"),
    INTERMADIATE("중급", "🌿"),
    ADVENCED("고급", "🌳"),
    EXPERT("전문가", "🏆");

    // 필드
    private final String description;
    private final String icon;
}
