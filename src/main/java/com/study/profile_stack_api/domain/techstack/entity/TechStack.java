package com.study.profile_stack_api.domain.techstack.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TechStack {
    private Long id;
    private Long profileId;
    private String name;
    private Category category;
    private Proficiency proficiency;
    private int yearsOfExp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}