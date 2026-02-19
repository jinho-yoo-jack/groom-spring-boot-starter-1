package com.study.profile_stack_api.domain.techstack.dto;

import com.study.profile_stack_api.domain.profile.dto.ProfileResponse;
import com.study.profile_stack_api.domain.profile.entity.Profile;
import com.study.profile_stack_api.domain.techstack.entity.Proficiency;
import com.study.profile_stack_api.domain.techstack.entity.TechCategory;
import com.study.profile_stack_api.domain.techstack.entity.TechStack;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechStackResponse {
    private Long id;
    private Long profileId;
    private String name;
    private String category;
    private String categoryIcon;
    private String proficiency;
    private String proficiencyIcon;
    private Integer yearsOfExp;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public static TechStackResponse from(TechStack techStack) {
        Proficiency proficiency = Proficiency.of(techStack.getProficiency());
        TechCategory techCategory = TechCategory.of(techStack.getCategory());

        return TechStackResponse.builder()
                .id(techStack.getId())
                .profileId(techStack.getProfileId())
                .name(techStack.getName())
                .category(techCategory.getDescription())
                .categoryIcon(techCategory.getIcon())
                .proficiency(proficiency.getDescription())
                .proficiencyIcon(proficiency.getIcon())
                .yearsOfExp(techStack.getYearsOfExp())
                .createdAt(techStack.getCreatedAt() != null ? techStack.getCreatedAt().toLocalDate() : LocalDate.now())
                .updatedAt(techStack.getUpdatedAt() != null ? techStack.getUpdatedAt().toLocalDate() : LocalDate.now())
                .build();
    }
}
