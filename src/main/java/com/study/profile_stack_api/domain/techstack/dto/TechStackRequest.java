package com.study.profile_stack_api.domain.techstack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TechStackRequest {
    private String name;
    private String category;
    private String proficiency;
    private Integer yearsOfExp;
}
