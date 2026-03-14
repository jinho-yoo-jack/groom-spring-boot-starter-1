package com.study.profile_stack_api.domain.tech_stack.dto.response;

import com.study.profile_stack_api.domain.tech_stack.entity.TechStack;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class TechStackResponse {
    Long id;
    Long profileId;
    String name;
    String category;
    String categoryIcon;
    String proficiency;
    String proficiencyIcon;
    Integer yearsOfExp;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public static TechStackResponse from(TechStack techStack) {
        return TechStackResponse.builder()
                .id(techStack.getId())
                .profileId(techStack.getProfileId())
                .name(techStack.getName())
                .category(techStack.getCategory().name())
                .categoryIcon(techStack.getCategory().getIcon())
                .proficiency(techStack.getProficiency().name())
                .proficiencyIcon(techStack.getProficiency().getIcon())
                .yearsOfExp(techStack.getYearsOfExp())
                .createdAt(techStack.getCreatedAt())
                .updatedAt(techStack.getUpdatedAt())
                .build();
    }
}
