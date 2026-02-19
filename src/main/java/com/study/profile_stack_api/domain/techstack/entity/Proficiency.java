package com.study.profile_stack_api.domain.techstack.entity;

import lombok.Getter;

@Getter
public enum Proficiency {
    BEGINNER("초급"),
    INTERMEDIATE("중급"),
    ADVANCED("고급"),
    EXPERT("전문가");

    private final String description;

    Proficiency(String description) {
        this.description = description;
    }
}