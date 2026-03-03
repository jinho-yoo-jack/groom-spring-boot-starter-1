package com.study.profile_stack_api.domain.techstack.dto.response;

import java.time.LocalDateTime;

import com.study.profile_stack_api.domain.techstack.entity.TechStack;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TechStackResponse {
    private Long id;
    private Long profileId;
    private String name;
    private String category;
    private String proficiency;
    private int yearsOfExp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TechStackResponse from(TechStack techStack) {
        return TechStackResponse.builder()
                .id(techStack.getId())
                .profileId(techStack.getProfileId())
                .name(techStack.getName())
                .category(techStack.getCategory().name())
                .proficiency(techStack.getProficiency().name())
                .yearsOfExp(techStack.getYearsOfExp())
                .createdAt(techStack.getCreatedAt())
                .updatedAt(techStack.getUpdatedAt())
                .build();
    }
}