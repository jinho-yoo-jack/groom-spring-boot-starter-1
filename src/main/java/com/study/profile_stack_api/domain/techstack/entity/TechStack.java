package com.study.profile_stack_api.domain.techstack.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 기술 스택 Entity
 */
@Getter
@Setter
public class TechStack {
    private Long id;
    private Long profileId;
    private String name;
    private TechCategory category;
    private Proficiency proficiency;
    private Integer yearsOfExp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TechStack(Long id, Long profileId, String name, TechCategory category, Proficiency proficiency, Integer yearsOfExp) {
        this.id = id;
        this.profileId = profileId;
        this.name = name;
        this.category = category;
        this.proficiency = proficiency;
        this.yearsOfExp = yearsOfExp;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
