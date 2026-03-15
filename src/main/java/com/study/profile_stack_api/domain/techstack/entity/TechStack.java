package com.study.profile_stack_api.domain.techstack.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 기술 스택 Entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechStack {
    private Long id;
    private Long profileId;
    private String name;
    private TechCategory category;
    private Proficiency proficiency;
    private Integer yearsOfExp;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();;
}
