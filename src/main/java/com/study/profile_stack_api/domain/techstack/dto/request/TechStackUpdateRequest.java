package com.study.profile_stack_api.domain.techstack.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TechStackUpdateRequest {
    private String name;
    private String category;
    private String proficiency;
    private int yearsOfExp;
}