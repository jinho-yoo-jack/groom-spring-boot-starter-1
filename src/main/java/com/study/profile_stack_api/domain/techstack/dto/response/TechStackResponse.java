package com.study.profile_stack_api.domain.techstack.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TechStackResponse {

    private long id;
    private long profileId;
    private String name;
    private String techCategory;
    private String techIcon;
    private String proficiency;
    private String proficiencyIcon;
    private int yearsOfExp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
