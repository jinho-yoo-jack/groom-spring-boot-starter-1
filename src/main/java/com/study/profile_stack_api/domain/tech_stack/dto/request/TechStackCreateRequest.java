package com.study.profile_stack_api.domain.tech_stack.dto.request;


import com.study.profile_stack_api.domain.tech_stack.entity.Category;
import com.study.profile_stack_api.domain.tech_stack.entity.Proficiency;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TechStackCreateRequest {
    private Long profileId;
    private String name;
    private String category;
    private String proficiency;
    private Integer yearsOfExp;
}
