package com.study.profile_stack_api.domain.techstack.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 기술 스택 응답 DTO
 */
@Getter
@JsonPropertyOrder({
        "id", "profileId", "name",
        "category", "categoryIcon",
        "proficiency", "proficiencyIcon",
        "yearsOfExp", "createdAt", "updatedAt"
})
public class TechStackResponse {
    private Long id;
    private Long profileId;
    private String name;
    private String category;
    private String categoryIcon;
    private String proficiency;
    private String proficiencyIcon;
    private Integer yearsOfExp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TechStackResponse from(TechStack techStack) {
        TechStackResponse response = new TechStackResponse();
        response.id = techStack.getId();
        response.profileId = techStack.getProfileId();
        response.name = techStack.getName();
        response.category = techStack.getCategory().name();
        response.categoryIcon = techStack.getCategory().getIcon();
        response.proficiency = techStack.getProficiency().name();
        response.proficiencyIcon = techStack.getProficiency().getIcon();
        response.yearsOfExp = techStack.getYearsOfExp();
        response.createdAt = techStack.getCreatedAt();
        response.updatedAt = techStack.getUpdatedAt();
        return response;
    }
}
